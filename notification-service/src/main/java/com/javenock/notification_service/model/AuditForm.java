package com.javenock.notification_service.model;


import com.fasterxml.jackson.annotation.JsonView;
import com.javenock.notification_service.model.dto.EntityType;
import com.javenock.notification_service.views.BaseView;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(schema = "hospital_v1", name = "audit_form")
public class AuditForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "action")
    @JsonView(BaseView.AuditView.class)
    private String action;

    @Column(name = "description")
    @JsonView(BaseView.AuditView.class)
    private String description;

    @Column(name = "comment")
    @JsonView(BaseView.AuditView.class)
    private String comment;

    @Column(name = "entity_id")
    @JsonView(BaseView.AuditView.class)
    private UUID entityId;

    @Column(name = "action_by")
    @JsonView(BaseView.AuditView.class)
    private UUID actionBy;

    @Column(name = "event_date")
    @JsonView(BaseView.AuditView.class)
    private LocalDateTime eventDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "entity_type")
    @JsonView(BaseView.AuditView.class)
    private EntityType entityType;
}
