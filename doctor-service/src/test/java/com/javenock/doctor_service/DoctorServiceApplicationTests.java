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

import static com.javenock.doctor_service.mock.UserManagementServicesMock.*;

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

    public String accessToken = "eyJraWQiOiJjODNlNWRlZi01YWM1LTRiOTctOTFlNy1lMDlkOGE2MzhlNGUiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJicm93c2VyLWNsaWVudCIsImF1ZCI6ImJyb3dzZXItY2xpZW50IiwibmJmIjoxNzI4NjM5OTE5LCJzY29wZSI6Im9wZW5pZCBwcm9maWxlIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgxIiwidXNlclB1YmxpY0lkIjoiZjZmZDdmOTktMTg1MC00MmE1LWI5YzgtODBmMTkwOGM2MjkyIiwiZXhwIjoxNzI4NzI2MzE5LCJpYXQiOjE3Mjg2Mzk5MTksImp0aSI6IjdmMDNhMDYxLWI4M2YtNDNkOC1hZTkzLWMyNmE3NjllYjIyYSIsImF1dGhvcml0aWVzIjpbIlJFQURfTUVNQkVSIiwiREVMRVRFX01FTUJFUiIsIlVQREFURV9NRU1CRVIiLCJDUkVBVEVfTUVNQkVSIiwiUkVBRF9ERVBBUlRNRU5UUyIsIlJFQURfQk9PS0lOR1MiLCJDUkVBVEVfQk9PS0lORyIsIlJFQURfQVVESVRfUkVDT1JEUyJdLCJ1c2VybmFtZSI6ImphdmVub2NrQWRtaW4ifQ.ZnYSkHjj6qDfQkryKcu-5RN_T7efrwXACvnzWUfG1x3cQEuUOrSfPuYIiDDwk78PlK8AvDpa7M03VoHAHDHo2-uzKcPAJ4XUp76eX7HyssH1pZR8aeK0eODqsrFlCYxJQHEMmI_f5HQMolBgqBvYmeKMB2HUjHtMKSNHms85xa9wyT-tnJbNDMoUmDkyA9iHv8n9_yxELZlOYf0E_nZsumVF8pbTmo6GxOMSYmN60XwHWx22ObPTxbGWUGNiwh7QtesvNQz8uar7YXIpG69K36n_h-l_UehjpoY9FaPfaZI3N_N8qvuTpgIOrRvegzReXPBtKtkjfvYv6i2Jjjqjxg";

    @Before
    public void setUpGlobal() throws IOException {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        setupMockGetUserPatient(mockPatientService, "bb874ce2-dc46-4f11-8915-c1d644f236df");
        setupMockGetUserDoctor(mockDoctorService, "41408776-ade3-404f-8df0-5ab7b38b8bff");
        setupMockGetUserByUsername(mockDoctorService, "javenockAdmin");
    }

}
