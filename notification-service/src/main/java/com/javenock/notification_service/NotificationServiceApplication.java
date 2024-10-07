package com.javenock.notification_service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;

import java.io.IOException;

@SpringBootApplication
public class NotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}

	@Bean
	public Module springDataPageModule() {
		return new SimpleModule().addSerializer(Page.class, new JsonSerializer<Page>() {
			@Override
			public void serialize(Page value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
				gen.writeStartObject();
				gen.writeNumberField("totalElements", value.getTotalElements());
				gen.writeNumberField("pageSize", value.getNumberOfElements());
				gen.writeNumberField("totalPages", value.getTotalPages());
				gen.writeBooleanField("last", value.isLast());
				gen.writeBooleanField("first", value.isFirst());
				gen.writeNumberField("pageNumber", value.getNumber());

				gen.writeFieldName("content");
				serializers.defaultSerializeValue(value.getContent(), gen);
				gen.writeEndObject();
			}
		});
	}
}
