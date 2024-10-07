package com.javenock.notification_service.events;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.LinkedHashMap;
import java.util.Map;

@JsonTypeName(AuditEventDefault._TYPE)
public class AuditEventDefault extends AuditEvent {
    public static final String _TYPE = "default";

    private final Map<String, Object> properties = new LinkedHashMap<>();

    public AuditEventDefault() {
        super();
    }

    @JsonAnySetter
    public void set(String name, Object value) {
        properties.put(name, value);
    }

    @JsonAnyGetter
    public Map<String, Object> properties() {
        return properties;
    }
}
