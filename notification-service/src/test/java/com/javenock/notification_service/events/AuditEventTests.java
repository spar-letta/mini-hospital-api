package com.javenock.notification_service.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javenock.notification_service.NotificationServiceApplicationTests;
import com.javenock.notification_service.configuration.EventConfig;
import com.javenock.notification_service.events.dto.PatientEventDto;
import com.javenock.notification_service.model.dto.EntityType;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.GenericMessage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@Slf4j
public class AuditEventTests extends NotificationServiceApplicationTests {
    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Test
    public void testAuditEvment() throws JsonProcessingException {
        given()
                .queryParam("entityType", EntityType.member)
                .queryParam("eventDate", LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .queryParam("action", "CREATION")
                .get("/auditReport")
                .then().log().all()
                .statusCode(200)
                .body("content.size()", greaterThanOrEqualTo(1));
    }
}
