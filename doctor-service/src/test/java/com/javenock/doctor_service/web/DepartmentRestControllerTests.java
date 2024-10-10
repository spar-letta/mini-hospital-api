package com.javenock.doctor_service.web;

import com.javenock.doctor_service.DoctorServiceApplicationTests;
import com.javenock.doctor_service.service.BatchJobService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

public class DepartmentRestControllerTests extends DoctorServiceApplicationTests {

    @Autowired
    private BatchJobService batchJobService;

    @Test
    public void shouldGetAllDepartments() {
        batchJobService.processDepartmentImport();
        given()
                .auth()
                .oauth2(accessToken)
                .queryParam("searchParam", "Radiology")
                .get("/departments")
                .then().log().all()
                .statusCode(200)
                .body("content.size()", greaterThanOrEqualTo(1))
                .body("content[0].name", equalToIgnoringCase("Radiology"));
    }
}
