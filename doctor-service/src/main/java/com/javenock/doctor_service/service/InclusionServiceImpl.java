package com.javenock.doctor_service.service;

import com.javenock.doctor_service.model.Department;
import com.javenock.doctor_service.model.Inclusion;
import com.javenock.doctor_service.repository.InclusionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InclusionServiceImpl implements InclusionService {

    private final InclusionRepository inclusionRepository;

    @Override
    public void createInclusionFromCsv(Department savedDepartment, Department department) {
        if (!department.getInclusionName().isEmpty()) {
            department.getInclusionName().forEach(item -> {
                String[] inclusionNames = item.split(",");
                Arrays.stream(inclusionNames).forEach(name -> {
                    Optional<Inclusion> optionalAuthor = inclusionRepository.findByNameIgnoreCaseAndDepartmentAndDeletedIsFalse(name, savedDepartment);
                    if (!optionalAuthor.isPresent()) {
                        Inclusion inclusion = new Inclusion();
                        inclusion.setName(name);
                        inclusion.setDepartment(savedDepartment);
                        inclusionRepository.save(inclusion);
                    }
                });
            });
        }
    }
}
