package com.javenock.doctor_service.model.dto;

import com.javenock.doctor_service.model.dataType.VisitType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class BookingCreateRequest {
    private VisitType visitType;
    private String reason;
    private LocalDateTime visitDate;
    private UUID departmentId;
    private UUID doctorId;
    private UUID patientId;
}
