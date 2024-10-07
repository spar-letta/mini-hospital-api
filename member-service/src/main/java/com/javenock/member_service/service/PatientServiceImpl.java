package com.javenock.member_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javenock.member_service.config.EventConfig;
import com.javenock.member_service.exceptions.BadRequestRestApiException;
import com.javenock.member_service.model.Patient;
import com.javenock.member_service.model.PatientAddress;
import com.javenock.member_service.model.dataType.ActionType;
import com.javenock.member_service.model.dataType.EntityType;
import com.javenock.member_service.model.dataType.Gender;
import com.javenock.member_service.model.dataType.MaritalStatus;
import com.javenock.member_service.model.dto.PatientAddressDto;
import com.javenock.member_service.model.dto.PatientCreateRequest;
import com.javenock.member_service.model.dto.PatientEventDto;
import com.javenock.member_service.repository.PatientAddressRepository;
import com.javenock.member_service.repository.PatientRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PatientServiceImpl implements PatientService {

    private static final Logger log = LoggerFactory.getLogger(PatientServiceImpl.class);
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientAddressRepository addressRepository;


    @Override
    public Patient createPatient(PatientCreateRequest patientCreateRequest) throws BadRequestRestApiException {
        if (patientCreateRequest.getNationalId() == null) {
            throw new BadRequestRestApiException("National ID is required");
        }

        Optional<Patient> optionalPatient = patientRepository.findByNationalIdAndDeletedIsFalse(patientCreateRequest.getNationalId());
        if (optionalPatient.isPresent()) {
            throw new BadRequestRestApiException("Patient already exists");
        }
        String action = "CREATE PATIENT";
        String description = "New patient registered";

        Patient patient = new Patient();
        patient.setNationalId(patientCreateRequest.getNationalId());
        patient.setFirstName(patientCreateRequest.getFirstName());
        patient.setLastName(patientCreateRequest.getLastName());
        patient.setOtherName(patientCreateRequest.getOtherName());
        patient.setGender(patientCreateRequest.getGender());
        patient.setDateOfBirth(patientCreateRequest.getDateOfBirth());
        patient.setEmailAddress(patientCreateRequest.getEmailAddress());
        patient.setPhoneNumber(patientCreateRequest.getPhoneNumber());
        patient.setDateOfBirth(patientCreateRequest.getDateOfBirth());
        patient.setMaritalStatus(patientCreateRequest.getMaritalStatus());
        Patient savedPatient = patientRepository.save(patient);

        if (patientCreateRequest.getPatientAddress() != null) {
            savePatientAddress(patientCreateRequest.getPatientAddress(), savedPatient);
        }
        PatientEventDto patientEventDto = convertToPatientEventDto(action, description, savedPatient.getFullName(), savedPatient.getPublicId(), null, EntityType.member);
        rabbitTemplate.convertAndSend(EventConfig.DIRECT_EXCHANGE, EventConfig.ROUTING_KEY_AUDIT, patientEventDto);
        return savedPatient;
    }

    private PatientEventDto convertToPatientEventDto(String action, String description, String comment, UUID entityId, UUID actionBy, EntityType entityType) {
        PatientEventDto patientEventDto = new PatientEventDto();
        patientEventDto.setAction(action);
        patientEventDto.setDescription(description);
        patientEventDto.setComment(comment);
        patientEventDto.setEntityId(entityId);
        patientEventDto.setActionBy(actionBy);
        patientEventDto.setEntityType(entityType);
        return patientEventDto;
    }

    @Override
    public Patient getPatient(UUID publicId) throws BadRequestRestApiException {
        return patientRepository.findByPublicIdAndDeletedIsFalse(publicId).orElseThrow(() -> new BadRequestRestApiException("Patient Not Fount"));
    }

    @Override
    public Page<Patient> getPatients(Long nationalId, String searchParam, Gender gender, MaritalStatus maritalStatus, Integer age, LocalDate dob, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Patient> cq = cb.createQuery(Patient.class);
        Root<Patient> root = cq.from(Patient.class);

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Patient> countRoot = countQuery.from(Patient.class);

        cq.distinct(true);

        final List<Predicate> andPredicates = new ArrayList<>();

        Predicate deletedPredicate = cb.equal(root.get("deleted"), Boolean.FALSE);
        andPredicates.add(deletedPredicate);

        if (nationalId != null) {
            Predicate newPredicate = cb.equal(root.get("nationalId"), nationalId);
            andPredicates.add(newPredicate);
        }

        if (gender != null) {
            Predicate newPredicate = cb.equal(root.get("gender"), gender);
            andPredicates.add(newPredicate);
        }

        if (maritalStatus != null) {
            Predicate newPredicate = cb.equal(root.get("maritalStatus"), maritalStatus);
            andPredicates.add(newPredicate);
        }

        if (dob != null) {
            Predicate newPredicate = cb.equal(root.get("dateOfBirth"), dob);
            andPredicates.add(newPredicate);
        }

        if (searchParam != null && searchParam.trim().length() >= 3) {
            final List<Predicate> orPredicates = new ArrayList<>();
            orPredicates.add(cb.like(cb.upper(root.get("firstName")), "%" + searchParam.toUpperCase() + "%"));
            orPredicates.add(cb.like(cb.upper(root.get("lastName")), "%" + searchParam.toUpperCase() + "%"));
            orPredicates.add(cb.like(cb.upper(root.get("otherName")), "%" + searchParam.toUpperCase() + "%"));
            Predicate p = cb.or(orPredicates.toArray(new Predicate[orPredicates.size()]));
            andPredicates.add(p);
        }

        cq.where(andPredicates.toArray(new Predicate[andPredicates.size()])).orderBy(cb.desc(root.get("id")));

        TypedQuery<Patient> query = entityManager.createQuery(cq).setMaxResults(pageable.getPageSize()).setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        List<Patient> queryResultList = query.getResultList();

        return new PageImpl<>(queryResultList, pageable, queryResultList.size());
    }

    @Override
    public Patient updatePatientDetails(UUID publicId, ActionType actionType, PatientCreateRequest patientCreateRequest) throws BadRequestRestApiException {
        Patient patient = getPatient(publicId);
        if (actionType == ActionType.BASIC_DETAILS) {
            patient.setNationalId(patientCreateRequest.getNationalId());
            patient.setFirstName(patientCreateRequest.getFirstName());
            patient.setLastName(patientCreateRequest.getLastName());
            patient.setOtherName(patientCreateRequest.getOtherName());
            patient.setGender(patientCreateRequest.getGender());
            patient.setDateOfBirth(patientCreateRequest.getDateOfBirth());
            patient.setEmailAddress(patientCreateRequest.getEmailAddress());
            patient.setPhoneNumber(patientCreateRequest.getPhoneNumber());
            patient.setDateOfBirth(patientCreateRequest.getDateOfBirth());
            patient.setMaritalStatus(patientCreateRequest.getMaritalStatus());
        }
        if (actionType == ActionType.ADDRESS_DETAILS) {
            savePatientAddress(patientCreateRequest.getPatientAddress(), patient);
        }
        return patientRepository.save(patient);
    }

    private void savePatientAddress(PatientAddressDto address, Patient savedPatient) {
        PatientAddress patientAddress = new PatientAddress();
        patientAddress.setPostalCode(address.getPostalCode());
        patientAddress.setCountry(address.getCountry());
        patientAddress.setPhysicalAddress(address.getPhysicalAddress());
        patientAddress.setPatient(savedPatient);
        addressRepository.save(patientAddress);
    }

}
