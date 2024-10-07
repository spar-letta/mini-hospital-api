package com.javenock.member_service.model.dto;

import com.javenock.member_service.model.dataType.EntityType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

@Setter
@Getter
@ToString
public class PatientEventDto implements Serializable {
    private String action;

    private String description;

    private String comment;

    private UUID entityId;

    private UUID actionBy;

    private EntityType entityType;
}
