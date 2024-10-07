package com.javenock.member_service.repository;

import com.javenock.member_service.model.PatientAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientAddressRepository extends JpaRepository<PatientAddress, Long> {
}
