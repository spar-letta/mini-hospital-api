package com.javenock.doctor_service.repository;

import com.javenock.doctor_service.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByNameIgnoreCaseAndDeletedIsFalse(String name);

    @Query("select d from Department d where d.deleted = false and lower(d.name) like %?1%")
    List<Department> findAllByDepartmentName(String searchParam);

    Optional<Department> findByPublicIdAndDeletedFalse(UUID departmentId);
}
