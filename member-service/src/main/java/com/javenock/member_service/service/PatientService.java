package com.javenock.member_service.service;

import com.javenock.member_service.exceptions.BadRequestRestApiException;
import com.javenock.member_service.model.Patient;
import com.javenock.member_service.model.dataType.ActionType;
import com.javenock.member_service.model.dataType.Gender;
import com.javenock.member_service.model.dataType.MaritalStatus;
import com.javenock.member_service.model.dto.PatientCreateRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.UUID;

public interface PatientService {
    Patient createPatient(PatientCreateRequest patientCreateRequest) throws BadRequestRestApiException;

    Patient getPatient(UUID publicId) throws BadRequestRestApiException;

    Page<Patient> getPatients(Long nationalId, String searchParam, Gender gender, MaritalStatus maritalStatus, Integer age, LocalDate dob, Pageable pageable);

    Patient updatePatientDetails(UUID publicId, ActionType actionType, PatientCreateRequest patientCreateRequest) throws BadRequestRestApiException;
}
