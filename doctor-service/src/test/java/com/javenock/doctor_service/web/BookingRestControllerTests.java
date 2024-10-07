package com.javenock.doctor_service.web;

import com.javenock.doctor_service.DoctorServiceApplicationTests;
import com.javenock.doctor_service.mock.UserManagementServicesMock;
import com.javenock.doctor_service.model.dataType.VisitType;
import com.javenock.doctor_service.model.dto.BookingCreateRequest;
import io.restassured.http.ContentType;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import static io.restassured.RestAssured.given;

public class BookingRestControllerTests extends DoctorServiceApplicationTests {

    @Test
    public void bookingTest() throws IOException {
        BookingCreateRequest request = new BookingCreateRequest();
        request.setReason("Treatment");
        request.setPatientId(UUID.fromString("78196df3-67de-497d-8165-6d20fac4a76a"));
        request.setDoctorId(UUID.fromString("054e1a02-9cd4-476b-bb4b-22fda2ef02df"));
        request.setVisitType(VisitType.in_patient);
        request.setVisitDate(LocalDateTime.now().plusDays(6));
        request.setDepartmentId(UUID.fromString("4616953c-cb4e-4536-b3ff-0132aea01995"));

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .post("/bookings")
                .then().log().all()
                .statusCode(200);
    }
}
