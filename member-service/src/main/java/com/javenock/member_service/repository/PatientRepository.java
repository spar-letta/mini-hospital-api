package com.javenock.member_service.repository;

import com.javenock.member_service.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByNationalIdAndDeletedIsFalse(Long nationalId);

    Optional<Patient> findByPublicIdAndDeletedIsFalse(UUID publicId);
}
