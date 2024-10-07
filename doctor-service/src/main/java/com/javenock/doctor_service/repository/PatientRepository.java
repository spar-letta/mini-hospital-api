package com.javenock.doctor_service.repository;

import com.javenock.doctor_service.model.vo.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByPublicIdAndDeletedIsFalse(UUID patientId);
}
