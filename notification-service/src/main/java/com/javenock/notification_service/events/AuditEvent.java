package com.javenock.notification_service.events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = AuditEventDefault.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AuditWriteEvent.class, name = AuditWriteEvent._TYPE),
})
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AuditEvent {
    private UUID id;
    private String eventTime;
}
