package com.javenock.doctor_service.utils.batch;

import com.javenock.doctor_service.exceptions.BadRequestRestApiException;
import com.javenock.doctor_service.model.Department;
import com.javenock.doctor_service.repository.DepartmentRepository;
import com.javenock.doctor_service.service.DepartmentService;
import com.javenock.doctor_service.service.InclusionService;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

public class DepartmentItemWriter implements ItemWriter<Department>, InitializingBean {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private InclusionService inclusionService;

    @Override
    public void write(Chunk<? extends Department> departmentList) throws Exception {
        departmentList.forEach(department -> {
            try {
                Department savedDepartment = departmentService.createDepartment(department);
                inclusionService.createInclusionFromCsv(savedDepartment, department);
            } catch (BadRequestRestApiException e) {
                throw new RuntimeException(e);
            }
        });
    }


    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
