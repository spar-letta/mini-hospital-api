package com.javenock.doctor_service.utils.runners;

import com.javenock.doctor_service.service.BatchJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DepartmentRunner implements CommandLineRunner {
    @Autowired
    private BatchJobService batchJobService;

    @Override
    public void run(String... args) throws Exception {
        batchJobService.processDepartmentImport();
    }
}
