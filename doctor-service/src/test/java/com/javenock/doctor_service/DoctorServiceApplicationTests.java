package com.javenock.doctor_service;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.javenock.doctor_service.config.WireMockConfig;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static com.javenock.doctor_service.mock.UserManagementServicesMock.setupMockGetUserDoctor;
import static com.javenock.doctor_service.mock.UserManagementServicesMock.setupMockGetUserPatient;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {WireMockConfig.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.main.allow-bean-definition-overriding=true")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@EnableConfigurationProperties
public abstract class DoctorServiceApplicationTests {

    @Value("${local.server.port}")
    public int port;

    @Autowired
    private WireMockServer mockDoctorService;

    @Autowired
    private WireMockServer mockPatientService;



    @Before
    public void setUpGlobal() throws IOException {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        setupMockGetUserPatient(mockPatientService, "bb874ce2-dc46-4f11-8915-c1d644f236df");
        setupMockGetUserDoctor(mockDoctorService, "41408776-ade3-404f-8df0-5ab7b38b8bff");

    }

}
