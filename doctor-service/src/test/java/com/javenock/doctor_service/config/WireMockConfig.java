package com.javenock.doctor_service.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@TestConfiguration
@ActiveProfiles("test")
public class WireMockConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public WireMockServer mockDoctorService() {
        return new WireMockServer(options().port(1030));
    }

//    @Bean(name="secondMockBooksService", initMethod = "start", destroyMethod = "stop")
//    public WireMockServer secondMockDoctorService() {
//        return new WireMockServer(options().port(1031));
//    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public WireMockServer mockPatientService() {
        return new WireMockServer(options().port(1031));
    }


}
