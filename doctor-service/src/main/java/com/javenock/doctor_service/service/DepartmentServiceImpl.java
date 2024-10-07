package com.javenock.doctor_service.service;

import com.javenock.doctor_service.exceptions.BadRequestRestApiException;
import com.javenock.doctor_service.model.Department;
import com.javenock.doctor_service.repository.DepartmentRepository;
import com.javenock.doctor_service.repository.InclusionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final InclusionRepository inclusionRepository;


    @Override
    public Department createDepartment(Department department) {
        Optional<Department> departmentOptional = departmentRepository.findByNameIgnoreCaseAndDeletedIsFalse(department.getName());
        if (!departmentOptional.isPresent()) {
            Department department1 = new Department();
            department1 = new Department();
            department1.setName(department.getName());
            department1.setDescription(department.getDescription());
            Department savedDepartment = departmentRepository.save(department1);
            return savedDepartment;
        }
        return departmentOptional.get();
    }

    @Override
    public Page<Department> fetchAllDepartments(String searchParam, Pageable pageable) {
        if (searchParam != null) {
            List<Department> departmentList = departmentRepository.findAllByDepartmentName(searchParam.toLowerCase());
            return new PageImpl<>(departmentList, pageable, departmentList.size());
        }
        return departmentRepository.findAll(pageable);
    }

    private Department getDepartmentByName(String name) throws BadRequestRestApiException {
        Optional<Department> departmentOptional = departmentRepository.findByNameIgnoreCaseAndDeletedIsFalse(name);
        if (!departmentOptional.isPresent()) {
            throw new BadRequestRestApiException("Department not found.");
        }
        return departmentOptional.get();
    }
}
