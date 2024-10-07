package com.javenock.doctor_service.service;

import org.springframework.batch.core.JobExecution;

import java.util.Optional;

public interface BatchJobService {
    Optional<JobExecution> processDepartmentImport();
}
