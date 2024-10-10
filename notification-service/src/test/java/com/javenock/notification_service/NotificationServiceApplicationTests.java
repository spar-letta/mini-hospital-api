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

	public String accessToken = "eyJraWQiOiIyMzAwYjkyNi02NzY3LTQ5ODItODRkMi04YjlmYjUwYmNmZWUiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJicm93c2VyLWNsaWVudCIsImF1ZCI6ImJyb3dzZXItY2xpZW50IiwibmJmIjoxNzI4NTU0MTg1LCJzY29wZSI6Im9wZW5pZCBwcm9maWxlIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgxIiwidXNlclB1YmxpY0lkIjoiMmIyNjRhMGQtNDM4NC00NDBlLWI3OTAtZjk5ZWI5YmZlYmVkIiwiZXhwIjoxNzI4NjQwNTg1LCJpYXQiOjE3Mjg1NTQxODUsImp0aSI6IjkwNjkwZjc4LWMzNDItNDZlYi1hYTRjLTM3NDJjZjU4NzA2OSIsImF1dGhvcml0aWVzIjpbIlJFQURfTUVNQkVSIiwiREVMRVRFX01FTUJFUiIsIlVQREFURV9NRU1CRVIiLCJDUkVBVEVfTUVNQkVSIl0sInVzZXJuYW1lIjoiamF2ZW5vY2tBZG1pbiJ9.XukASmzL1RE6pI0VBdDYSdiGwX-wqi0DbGud8jvtX6cNTkEttzs9N2iZpTHFjkMBpWeGTAIqsEPL3Bm3QEUZj_7b2K8OfouIASpahjb1_FPi9CqpIWSJjBeUyj05Fenei0DtskPZG17caIKhP5U6748ygASZXu9Tnf0HHJ-Au5ciH5C_3lKLGCEDq54ennASm6xNWH6FoyqErSA3YU7BTTxtVF6ynYAw2UoFDR5s7rA6gCPvhwSYZUQVHPfQjKDsdq-H9ymQo4RML3SZ1yjrD9ceEkrzp1fY8WrPp5YY7JOmqkxJD7C_ZKFs8Q98NgdnliU5oXzEtbMHjb-NFK1YsA";

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
