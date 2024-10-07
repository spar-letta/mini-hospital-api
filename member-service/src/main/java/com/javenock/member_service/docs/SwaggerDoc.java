package com.javenock.member_service.docs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerDoc {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spring Boot 3 API")
                        .version("1.0")
                        .description("Hospital Management")
                        .contact(new Contact()
                                .name("javenock")
                                .email("simiyuenock1990@gmail.com")
                                .url("https://javenock-portifolio.netlify.app")));
    }
}

//@Bean
//public OpenAPI customOpenAPI() {
//    return new OpenAPI()
//            .components(new Components()
//                    .addSecuritySchemes("basicScheme", new SecurityScheme()
//                            .type(SecurityScheme.Type.HTTP)
//                            .scheme("basic")))
//            .addSecurityItem(new SecurityRequirement().addList("basicScheme"))
//            .info(new Info()
//                    .title("Spring Boot 3 API")
//                    .version("1.0")
//                    .description("Sample Spring Boot 3"));
