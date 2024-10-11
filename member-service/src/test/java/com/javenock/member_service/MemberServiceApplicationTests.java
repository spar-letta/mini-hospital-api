package com.javenock.member_service;

import com.javenock.member_service.model.dataType.Gender;
import com.javenock.member_service.model.dataType.MaritalStatus;
import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public abstract class MemberServiceApplicationTests {

    public String accessToken = "eyJraWQiOiJjODNlNWRlZi01YWM1LTRiOTctOTFlNy1lMDlkOGE2MzhlNGUiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJicm93c2VyLWNsaWVudCIsImF1ZCI6ImJyb3dzZXItY2xpZW50IiwibmJmIjoxNzI4NjM5OTE5LCJzY29wZSI6Im9wZW5pZCBwcm9maWxlIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgxIiwidXNlclB1YmxpY0lkIjoiZjZmZDdmOTktMTg1MC00MmE1LWI5YzgtODBmMTkwOGM2MjkyIiwiZXhwIjoxNzI4NzI2MzE5LCJpYXQiOjE3Mjg2Mzk5MTksImp0aSI6IjdmMDNhMDYxLWI4M2YtNDNkOC1hZTkzLWMyNmE3NjllYjIyYSIsImF1dGhvcml0aWVzIjpbIlJFQURfTUVNQkVSIiwiREVMRVRFX01FTUJFUiIsIlVQREFURV9NRU1CRVIiLCJDUkVBVEVfTUVNQkVSIiwiUkVBRF9ERVBBUlRNRU5UUyIsIlJFQURfQk9PS0lOR1MiLCJDUkVBVEVfQk9PS0lORyIsIlJFQURfQVVESVRfUkVDT1JEUyJdLCJ1c2VybmFtZSI6ImphdmVub2NrQWRtaW4ifQ.ZnYSkHjj6qDfQkryKcu-5RN_T7efrwXACvnzWUfG1x3cQEuUOrSfPuYIiDDwk78PlK8AvDpa7M03VoHAHDHo2-uzKcPAJ4XUp76eX7HyssH1pZR8aeK0eODqsrFlCYxJQHEMmI_f5HQMolBgqBvYmeKMB2HUjHtMKSNHms85xa9wyT-tnJbNDMoUmDkyA9iHv8n9_yxELZlOYf0E_nZsumVF8pbTmo6GxOMSYmN60XwHWx22ObPTxbGWUGNiwh7QtesvNQz8uar7YXIpG69K36n_h-l_UehjpoY9FaPfaZI3N_N8qvuTpgIOrRvegzReXPBtKtkjfvYv6i2Jjjqjxg";

    @Value("${local.server.port}")
    public int port;

    @Autowired
    private WebApplicationContext context;

//	@Autowired
//	private Filter springSecurityFilterChain;

    public MockMvc mvc;

    @Before
    public void setUpGlobal() throws IOException {
        RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;

        mvc = MockMvcBuilders
                .webAppContextSetup(context)
//				.apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    public String createPatientPayload(String nationalId, String firstName, String lastName, String otherName, Gender gender, String emailAddress, String phoneNumber, LocalDate dateOfBirth, MaritalStatus maritalStatus, String postalCode, String physicalAddress, String country) {
        return String.format("{\n" +
                "  \"nationalId\": \"%s\",\n" +
                "  \"firstName\": \"%s\",\n" +
                "  \"lastName\": \"%s\",\n" +
                "  \"otherName\": \"%s\",\n" +
                "  \"gender\": \"%s\",\n" +
                "  \"emailAddress\": \"%s\",\n" +
                "  \"phoneNumber\": \"%s\",\n" +
                "  \"dateOfBirth\": \"%s\",\n" +
                "  \"maritalStatus\": \"%s\",\n" +
                "  \"patientAddress\": {\n" +
                "    \"postalCode\": \"%s\",\n" +
                "    \"physicalAddress\": \"%s\",\n" +
                "    \"country\": \"%s\"\n" +
                "  }\n" +
                "}", nationalId, firstName, lastName, otherName, gender, emailAddress, phoneNumber, dateOfBirth.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), maritalStatus, postalCode, physicalAddress, country);
    }

}