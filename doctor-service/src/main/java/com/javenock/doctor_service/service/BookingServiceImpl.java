package com.javenock.doctor_service.service;

import com.javenock.doctor_service.model.Booking;
import com.javenock.doctor_service.model.Department;
import com.javenock.doctor_service.model.dataType.BookingStatus;
import com.javenock.doctor_service.model.dataType.CounterType;
import com.javenock.doctor_service.model.dto.BookingCreateRequest;
import com.javenock.doctor_service.model.vo.Patient;
import com.javenock.doctor_service.model.vo.User;
import com.javenock.doctor_service.repository.BookingRepository;
import com.javenock.doctor_service.repository.DepartmentRepository;
import com.javenock.doctor_service.repository.PatientRepository;
import com.javenock.doctor_service.repository.UserRepository;
import com.javenock.doctor_service.utils.feign.UserServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserServiceClient userServiceClient;
    private final DepartmentRepository departmentRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final CounterService counterService;

    @Override
    public Booking createBooking(BookingCreateRequest bookingCreateRequest, Authentication loggedInUser) throws BadRequestException {
        String name = loggedInUser.getName();
        User createdBy = userServiceClient.fetchUserByUsername(name).orElse(null);
        Booking booking = new Booking();
        if (bookingCreateRequest.getDepartmentId() == null)
            throw new BadRequestException("Sorry missing department");

        if (bookingCreateRequest.getPatientId() == null)
            throw new BadRequestException("Sorry missing patient");

        if (bookingCreateRequest.getDoctorId() == null)
            throw new BadRequestException("Sorry missing doctor");

        if (bookingCreateRequest.getVisitDate() == null)
            throw new BadRequestException("Sorry missing visit date");

        if (bookingCreateRequest.getVisitType() == null)
            throw new BadRequestException("Sorry missing visit type");

        if (bookingCreateRequest.getReason() == null)
            throw new BadRequestException("Sorry missing reason for booking");

        Department optionalDepartment = departmentRepository.findByPublicIdAndDeletedFalse(bookingCreateRequest.getDepartmentId()).orElseThrow(() -> new BadRequestException("Sorry, department not found."));

        Patient patient = patientRepository.findByPublicIdAndDeletedIsFalse(bookingCreateRequest.getPatientId()).orElseThrow(() -> new BadRequestException("Sorry, patient not found."));
        User doctor = userRepository.findByPublicIdAndDeletedIsFalse(bookingCreateRequest.getDoctorId()).orElseThrow(() -> new BadRequestException("Sorry, doctor not found."));
        booking.setVisitType(bookingCreateRequest.getVisitType());
        booking.setReason(Arrays.asList(bookingCreateRequest.getReason()));
        booking.setVisitDate(bookingCreateRequest.getVisitDate().atStartOfDay());
        booking.setDepartment(optionalDepartment);
        booking.setDoctor(Arrays.asList(doctor));
        booking.setStatus(BookingStatus.PENDING);
        booking.setPatient(patient);
        booking.setCreatedBy(createdBy);
        booking.setBookingNumber(String.format("VB-%d", counterService.getNextCounter(CounterType.VISIT_BOOKING)));

        return bookingRepository.save(booking);
    }

    @Override
    public Page<Booking> getAllUsers(Pageable pageable, Authentication loggedInUser) {
        return bookingRepository.findAll(pageable);
    }

}
