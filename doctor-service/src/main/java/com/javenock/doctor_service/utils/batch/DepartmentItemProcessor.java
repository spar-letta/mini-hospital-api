package com.javenock.doctor_service.utils.batch;

import com.javenock.doctor_service.model.Department;
import org.springframework.batch.item.ItemProcessor;

import java.util.List;

public class DepartmentItemProcessor implements ItemProcessor<Department, Department>{
    @Override
    public Department process(Department item) throws Exception {
        final String name = item.getName();
        final String description = item.getDescription();
        final List<String> inclusions = item.getInclusionName();
        return new Department(name, description, inclusions);
    }
}
