package com.javenock.member_service.web;

import com.javenock.member_service.MemberServiceApplicationTests;
import com.javenock.member_service.model.dataType.ActionType;
import com.javenock.member_service.model.dataType.Gender;
import com.javenock.member_service.model.dataType.MaritalStatus;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PatientRestControllerTests extends MemberServiceApplicationTests {

    public String createPatient(String payload) {
        return given()
                .contentType("application/json")
                .body(payload).log().all()
                .post("/patients")
                .then()
                .statusCode(200)
                .body("publicId", notNullValue())
                .extract().path("publicId");
    }

    @Test
    public void testCreatePatient() {
        String patientPayload = createPatientPayload("9222229999", "kamau", "name2", "jina nyingine", Gender.MALE, "kamau.name2@gmail.com", "0786512343", LocalDate.now().minusYears(20), MaritalStatus.MARRIED, "50200", "Malysie Kenya", "KENYA");
        String patientPublicId = createPatient(patientPayload);
        given()
                .contentType("application/json")
                .get("/patients/{publicId}", UUID.fromString(patientPublicId))
                .then().log().all()
                .statusCode(200)
                .body("nationalId", equalTo(9222229999L))
                .body("firstName", equalToIgnoringCase("kamau"))
                .body("lastName", equalToIgnoringCase("name2"))
                .body("otherName", equalToIgnoringCase("jina nyingine"))
                .body("emailAddress", equalToIgnoringCase("kamau.name2@gmail.com"))
                .body("phoneNumber", equalTo("0786512343"))
                .body("gender", equalTo("MALE"))
                .body("dateOfBirth", equalTo(LocalDate.now().minusYears(20).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))))
                .body("maritalStatus", equalTo("MARRIED"))
                .body("address[0].postalCode", equalTo("50200"))
                .body("address[0].physicalAddress", equalToIgnoringCase("Malysie Kenya"))
                .body("address[0].country", equalToIgnoringCase("KENYA"));

        given()
                .queryParam("searchParam", "kamau")
                .queryParam("gender", "MALE")
                .get("/patients")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    public void testUpadatePatientWorks() {
        String patientPayload = createPatientPayload("1000001", "Caren", "Mwakili", "jina nyingine", Gender.FEMALE, "caren2@gmail.com", "0708543231", LocalDate.now().minusYears(23), MaritalStatus.SINGLE, "24000", "Mayanja", "KENYA");
        String patientPublicId = createPatient(patientPayload);
        given()
                .contentType("application/json")
                .get("/patients/{publicId}", UUID.fromString(patientPublicId))
                .then().log().all()
                .statusCode(200)
                .body("nationalId", equalTo(1000001))
                .body("firstName", equalToIgnoringCase("Caren"))
                .body("lastName", equalToIgnoringCase("Mwakili"))
                .body("fullName", equalToIgnoringCase("Caren Mwakili jina nyingine"))
                .extract().path("publicId");

        String updatePatientBasicPayload = createPatientPayload("1001111", "Pamela", "Mwakili", "Jimon nyingine", Gender.FEMALE, "caren2@gmail.com", "0708543231", LocalDate.now().minusYears(23), MaritalStatus.SINGLE, "24000", "Mayanja", "KENYA");

        given()
                .queryParam("actionType", ActionType.BASIC_DETAILS)
                .contentType("application/json")
                .body(updatePatientBasicPayload).log().all()
                .put("/patients/{publicId}", UUID.fromString(patientPublicId))
                .then().log().all()
                .statusCode(200)
                .body("nationalId", equalTo(1001111))
                .body("firstName", equalToIgnoringCase("Pamela"))
                .body("lastName", equalToIgnoringCase("Mwakili"))
                .body("fullName", equalToIgnoringCase("Pamela Mwakili Jimon nyingine"));

        String updatePatientAddressPayload = createPatientPayload("1001111", "Pamela", "Mwakili", "Jimon nyingine", Gender.FEMALE, "caren2@gmail.com", "0708543231", LocalDate.now().minusYears(23), MaritalStatus.SINGLE, "70-2000", "Mwea Soko", "TANZANIA");

        given()
                .queryParam("actionType", ActionType.ADDRESS_DETAILS)
                .contentType("application/json")
                .body(updatePatientAddressPayload).log().all()
                .put("/patients/{publicId}", UUID.fromString(patientPublicId))
                .then().log().all()
                .statusCode(200)
                .body("nationalId", equalTo(1001111))
                .body("address[0].postalCode", equalTo("70-2000"))
                .body("address[0].physicalAddress", equalToIgnoringCase("Mwea Soko"))
                .body("address[0].country", equalToIgnoringCase("TANZANIA"));
    }
}
