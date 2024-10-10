package com.javenock.doctor_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class BatchJobServiceImpl implements BatchJobService {
    private final JobLauncher jobLauncher;
    private final Job departmentJob;

    public BatchJobServiceImpl(JobLauncher jobLauncher, Job departmentJob) {
        this.jobLauncher = jobLauncher;
        this.departmentJob = departmentJob;
    }

    JobParameters parameters = new JobParametersBuilder().addLong("Start-At" ,System.currentTimeMillis())
            .toJobParameters();


    @Override
    public Optional<JobExecution> processDepartmentImport() {
        log.info("Start of department import Job >>>>>");

        try {
            return Optional.of(jobLauncher.run(departmentJob, parameters));
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
