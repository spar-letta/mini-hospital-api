package com.javenock.notification_service.events;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.javenock.notification_service.events.dto.PatientEventDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonTypeName(AuditWriteEvent._TYPE)
public class AuditWriteEvent extends AuditEvent{
    public static final String _TYPE = "auditWriteEvent";

    private PatientEventDto subject;

    public AuditWriteEvent(PatientEventDto c) {
        super();
        this.subject = c;
    }
}
