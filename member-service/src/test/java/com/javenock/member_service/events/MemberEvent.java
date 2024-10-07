package com.javenock.member_service.events;

import com.javenock.member_service.MemberServiceApplicationTests;
import com.javenock.member_service.config.EventConfig;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.Message;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.mockito.Mockito.mock;

public class MemberEvent extends MemberServiceApplicationTests {

    @Captor
    private ArgumentCaptor<Message> amqpMessage;

    @Mock
    RabbitTemplate rabbitTemplate;

    @Test
    @Ignore
    public void testAuditEventSendPatientCreated() throws IOException, TimeoutException {
        ConnectionFactory mockConnectionFactory = mock(ConnectionFactory.class);
        Connection mockConnection = mock(Connection.class);

        String payload = "{\n" +
                "    \"publicId\": \"74f7a493-0398-4e0d-ac59-f62dfa1baa71\",\n" +
                "    \"dateCreated\": \"2024-09-30 17:10:33\",\n" +
                "    \"createdBy\": null,\n" +
                "    \"modifiedBy\": null,\n" +
                "    \"deleted\": false,\n" +
                "    \"nationalId\": 9222229999,\n" +
                "    \"firstName\": \"kamau\",\n" +
                "    \"lastName\": \"name2\",\n" +
                "    \"otherName\": \"jina nyingine\",\n" +
                "    \"gender\": \"MALE\",\n" +
                "    \"emailAddress\": \"kamau.name2@gmail.com\",\n" +
                "    \"phoneNumber\": \"0786512343\",\n" +
                "    \"dateOfBirth\": \"30/09/2004\",\n" +
                "    \"maritalStatus\": \"MARRIED\",\n" +
                "    \"address\": [\n" +
                "        {\n" +
                "            \"postalCode\": \"50200\",\n" +
                "            \"physicalAddress\": \"Malysie Kenya\",\n" +
                "            \"country\": \"KENYA\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"fullName\": \"kamau name2 jina nyingine\",\n" +
                "    \"age\": 20\n" +
                "}";
        rabbitTemplate.convertAndSend(EventConfig.DIRECT_EXCHANGE, EventConfig.ROUTING_KEY_AUDIT, payload);

        rabbitTemplate.convertAndSend(EventConfig.DIRECT_EXCHANGE, EventConfig.AUDIT_QUEUE, payload);
//        assertTextMessage(amqpMessage.getValue());
    }
}
