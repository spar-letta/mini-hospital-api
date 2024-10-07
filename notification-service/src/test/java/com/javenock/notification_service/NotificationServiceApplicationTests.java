package com.javenock.notification_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Slf4j
public abstract class NotificationServiceApplicationTests {

	@Value("${local.server.port}")
	public int port;

	public MockMvc mvc;

	public ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private WebApplicationContext context;

	@Before
	public void setUpGlobal() throws IOException {
		RestAssured.port = port;
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		String password = "password";

		RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();

		mvc = MockMvcBuilders
				.webAppContextSetup(context)
//				.addFilters(springSecurityFilterChain)
				.build();
//		try {
//			//temp to allow testing with rabbitmq configs
//			AMQPServerMock server = new AMQPServerMock(defaultConfig().withKnownHosts("local.vh"));
//			server.start();
//		} catch (Exception e) {
//			log.error(e.getMessage());
//		}
	}

}
