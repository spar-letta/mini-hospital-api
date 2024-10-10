package com.javenock.doctor_service.mock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.UUID;

import static java.nio.charset.Charset.defaultCharset;
import static org.springframework.util.StreamUtils.copyToString;

@Slf4j
public class UserManagementServicesMock {
    public static void setupMockGetUserPatient(WireMockServer mockService, String patientId) throws IOException {
        mockService.stubFor(WireMock.get(WireMock.urlEqualTo(String.format("/internal/patient/%s", patientId)))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(copyToString(
                                UserManagementServicesMock.class.getClassLoader().getResourceAsStream("payload/fetchUserPatientOk.json"),
                                defaultCharset()))));
    }

    public static void setupMockGetUserDoctor(WireMockServer mockService, String userId) throws IOException {
        mockService.stubFor(WireMock.get(WireMock.urlEqualTo(String.format("/internal/user/%s", userId)))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(copyToString(
                                UserManagementServicesMock.class.getClassLoader().getResourceAsStream("payload/fetchUserDoctorOk.json"),
                                defaultCharset()))));
    }

    public static void setupMockGetUserByUsername(WireMockServer mockService, String username) throws IOException {
        mockService.stubFor(WireMock.get(WireMock.urlEqualTo(String.format("/internal/user?username=%s", username)))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(copyToString(
                                UserManagementServicesMock.class.getClassLoader().getResourceAsStream("payload/fetchUserByUsernameOk.json"),
                                defaultCharset()))));
    }
}
