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

    public String accessToken = "eyJraWQiOiIwMWFkNjA1NC1iYTViLTRjNGYtYjIzYi0zY2JkZTdjMTQ2NWIiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJicm93c2VyLWNsaWVudCIsImF1ZCI6ImJyb3dzZXItY2xpZW50IiwibmJmIjoxNzI4NDgxMDgxLCJzY29wZSI6Im9wZW5pZCBwcm9maWxlIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgxIiwidXNlclB1YmxpY0lkIjoiMThkNWYxMTItZGRiNS00ZGQzLThkNTktYzU1OGMwZDljZjc5IiwiZXhwIjoxNzI4NTY3NDgxLCJpYXQiOjE3Mjg0ODEwODEsImp0aSI6ImIyNWFiZjdhLWU3YmQtNGEyOS04NTI4LTcwZGZmZDBhYjg3NSIsImF1dGhvcml0aWVzIjpbIlJFQURfTUVNQkVSIiwiREVMRVRFX01FTUJFUiIsIlVQREFURV9NRU1CRVIiLCJDUkVBVEVfTUVNQkVSIl0sInVzZXJuYW1lIjoiamF2ZW5vY2tBZG1pbiJ9.RMXCePwNsCPGjp9kUdkTDo9cizkUzUOYhBFnjUdAC72HqSsh-sgh4bHH_dwvaf5DePRvg326cejbo1gA_UWbhNtWB1H-NXqM-8okRWTkNwDTEXRlnhhvlIkCSUVnbjw1-3lxbtPPMJ7tJLz7JPN4827gnCOH061oCSCV1gNQccZbVdrV-P2k_hKFhhevJiTdjEtLmxzDvUfCq2sFYxkGS_BO9KK_WhVOaPynIwFXo8AM23e37-plRW_RaHXLBLBybJIeFdKnax8Va6E2gxvF-bb7SHzEOIeOnkS1fV0gCOb50gEr-r7T3TSCeH3MQv-HG5zzE4E0yJpLvb8V8IQq5w";

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