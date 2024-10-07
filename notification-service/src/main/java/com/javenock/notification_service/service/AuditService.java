package com.javenock.notification_service.service;

import com.javenock.notification_service.events.dto.PatientEventDto;
import com.javenock.notification_service.model.AuditForm;
import com.javenock.notification_service.model.dto.EntityType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.UUID;

public interface AuditService {
    void saveReportRecord(PatientEventDto subject);

    Page<AuditForm> fetchAuditReport(UUID actionedBy, EntityType entityType, String action, LocalDate eventDate, Pageable pageable);
}
