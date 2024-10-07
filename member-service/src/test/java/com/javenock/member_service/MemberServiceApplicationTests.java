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