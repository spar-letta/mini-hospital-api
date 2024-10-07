package com.javenock.doctor_service.service;

import com.javenock.doctor_service.exceptions.BadRequestRestApiException;
import com.javenock.doctor_service.model.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DepartmentService {
    Department createDepartment(Department department) throws BadRequestRestApiException;

    Page<Department> fetchAllDepartments(String searchParam, Pageable pageable);
}
