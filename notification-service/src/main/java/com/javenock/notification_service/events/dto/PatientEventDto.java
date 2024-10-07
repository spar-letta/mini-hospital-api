package com.javenock.notification_service.events.dto;

import com.javenock.notification_service.model.dto.EntityType;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PatientEventDto {
    private String action;

    private String description;

    private String comment;

    private UUID entityId;

    private UUID actionBy;

    private EntityType entityType;
}
