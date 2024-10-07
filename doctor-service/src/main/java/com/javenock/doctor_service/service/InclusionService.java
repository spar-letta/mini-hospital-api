package com.javenock.doctor_service.service;

import com.javenock.doctor_service.model.Department;

public interface InclusionService {
    void createInclusionFromCsv(Department savedDepartment, Department department);
}
