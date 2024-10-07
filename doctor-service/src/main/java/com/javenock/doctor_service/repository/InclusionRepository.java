package com.javenock.doctor_service.repository;

import com.javenock.doctor_service.model.Department;
import com.javenock.doctor_service.model.Inclusion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InclusionRepository extends JpaRepository<Inclusion, Long> {

    Optional<Inclusion> findByNameIgnoreCaseAndDepartmentAndDeletedIsFalse(String name, Department savedDepartment);
}
