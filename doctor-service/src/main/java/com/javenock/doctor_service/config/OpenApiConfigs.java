package com.javenock.doctor_service.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class OpenApiConfigs {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("user-management-service doc")
                        .version("1.0")
                        .description("Hospital Management")
                        .contact(new Contact()
                                .name("javenock")
                                .email("simiyuenock1990@gmail.com")
                                .url("https://javenock-portifolio.netlify.app")));
    }

//    @Bean
//    public OpenAPI userOpenAPI(
//            @Value("${openapi.service.title}") String serviceTitle,
//            @Value("${openapi.service.version}") String serviceVersion,
//            @Value("${openapi.service.url}") String url) {
//        return new OpenAPI()
//                .servers(List.of(new Server().url(url)))
//                .info(new Info().title(serviceTitle).version(serviceVersion));
//    }
}
