package com.javenock.notification_service.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javenock.notification_service.configuration.EventConfig;
import com.javenock.notification_service.events.dto.PatientEventDto;
import com.javenock.notification_service.service.AuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class handleNotificationEvents {

    @Autowired
    private AuditService auditService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = EventConfig.AUDIT_QUEUE)
    public void handlePostReportEvent(PatientEventDto patientEventDto) {
        try {
            auditService.saveReportRecord(patientEventDto);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
