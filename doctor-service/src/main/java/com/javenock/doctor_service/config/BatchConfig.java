package com.javenock.doctor_service.config;

import com.javenock.doctor_service.model.Department;
import com.javenock.doctor_service.utils.batch.DepartmentItemProcessor;
import com.javenock.doctor_service.utils.batch.DepartmentItemWriter;
import com.javenock.doctor_service.utils.batch.TaskLetTest;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {
    @Bean
    public FlatFileItemReader<Department> DepartmentItemReader() {
        FlatFileItemReader<Department> reader = new FlatFileItemReader<Department>();
        reader.setResource(new ClassPathResource("department.csv"));
        reader.setLinesToSkip(1);
        reader.setLineMapper(new DefaultLineMapper<Department>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[]{"name", "description", "inclusionName"});
                setDelimiter(":");
            }});

            setFieldSetMapper(new BeanWrapperFieldSetMapper<Department>() {{
                setTargetType(Department.class);
            }});
        }});
        return reader;
    }

    //processor
    @Bean
    public DepartmentItemProcessor departmentItemProcessor() {
        return new DepartmentItemProcessor();
    }

    //writer
    @Bean
    public DepartmentItemWriter departmentItemWriter() {
        return new DepartmentItemWriter();
    }

    //step
    @Bean
    public Step stepDepartment(JobRepository jobRepository, PlatformTransactionManager transactionManage) {
        var step = new StepBuilder("stepProduct", jobRepository)
                .<Department, Department>chunk(10, transactionManage)
                .reader(DepartmentItemReader())
                .processor(departmentItemProcessor())
                .writer(departmentItemWriter())
                .build();
        return step;
    }

    //step 2 use a tasklet
    @Bean
    public Tasklet taskletTest() {
        return new TaskLetTest();
    }


    @Bean
    public Step stepTest(JobRepository jobRepository, PlatformTransactionManager transactionManage) {
        var step = new StepBuilder("stepTest", jobRepository)
                .tasklet(taskletTest(), transactionManage)
                .build();
        return step;
    }

    //job
    @Bean
    public Job departmentJob(JobRepository jobRepository,
                          @Qualifier("stepDepartment") Step step1,
                          @Qualifier("stepTest") Step step2) {
        return new JobBuilder("productJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step1)
                .next(step2)
                .build();
    }
}
