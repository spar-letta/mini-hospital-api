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

    public String accessToken = "eyJraWQiOiI0Y2YyZDA1My1mMzQ3LTQ2NjItODQzOC0xZDBiYTZjMjA1Y2EiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJicm93c2VyLWNsaWVudCIsImF1ZCI6ImJyb3dzZXItY2xpZW50IiwibmJmIjoxNzI4NTcxMTg2LCJzY29wZSI6Im9wZW5pZCBwcm9maWxlIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgxIiwidXNlclB1YmxpY0lkIjoiZjZmZDdmOTktMTg1MC00MmE1LWI5YzgtODBmMTkwOGM2MjkyIiwiZXhwIjoxNzI4NjU3NTg2LCJpYXQiOjE3Mjg1NzExODYsImp0aSI6IjVhNGVhZmFiLTRkYWEtNDNiZC04ZDU5LWU0NzAwNDg2NDBiZiIsImF1dGhvcml0aWVzIjpbIlJFQURfTUVNQkVSIiwiREVMRVRFX01FTUJFUiIsIlVQREFURV9NRU1CRVIiLCJDUkVBVEVfTUVNQkVSIiwiUkVBRF9ERVBBUlRNRU5UUyIsIlJFQURfQk9PS0lOR1MiLCJDUkVBVEVfQk9PS0lORyIsIlJFQURfQVVESVRfUkVDT1JEUyJdLCJ1c2VybmFtZSI6ImphdmVub2NrQWRtaW4ifQ.WDjOWAlYFPFul7cHHoV64RuSQaPP3MsB_zWTRdVrb_TVzz8vsbhoiwsInvF8B29ydhnhKTWNI2E7m-L9yZetDSavpWlxf6inqrML1tKHgCPWMHOATzuKQdhj84lTy3zyRbMchF9MhzegJlg-ivIPw6zrj6lf_YAow4lqicNgitA2wjuHQUaT0M9wk9qSXjdLe4VtiXunP3o4RsEUDAmtMUBehWgVXF2pFKA6c1MfaOYRMvkrX8QHXjHCOi_dTY6v679ZqYejkIbPuUaLNvQER2z4tJiJVNtkyhokewRJ5SExI3rGKUnneHPvRvIR_8uyEyWXrpdncryB0vJZQaWD4A";


    @Before
    public void setUpGlobal() throws IOException {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        setupMockGetUserPatient(mockPatientService, "bb874ce2-dc46-4f11-8915-c1d644f236df");
        setupMockGetUserDoctor(mockDoctorService, "41408776-ade3-404f-8df0-5ab7b38b8bff");
        setupMockGetUserByUsername(mockDoctorService, "javenockAdmin");
    }

}
