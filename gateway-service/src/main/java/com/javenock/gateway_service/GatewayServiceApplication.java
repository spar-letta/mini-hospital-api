package com.javenock.gateway_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

@EnableDiscoveryClient
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "API Gateway", version = "1.0", description = "Documentation API Gateway v1.0"))
public class GatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route(r -> r.path("/doctor-service/v3/api-docs").and().method(HttpMethod.GET).uri("lb://doctor-service"))
                .route(r -> r.path("/member-service/v3/api-docs").and().method(HttpMethod.GET).uri("lb://member-service"))
                .route(r -> r.path("/notification-service/v3/api-docs").and().method(HttpMethod.GET).uri("lb://notification-service"))
                .route(r -> r.path("/user-management-service/v3/api-docs").and().method(HttpMethod.GET).uri("lb://user-management-service"))
                .build();
    }

}