package auth.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@OpenAPIDefinition
@Configuration
public class SwaggerDoc {
    @Bean
    public OpenAPI userOpenAPI(
            @Value("${openapi.service.title}") String serviceTitle,
            @Value("${openapi.service.version}") String serviceVersion,
            @Value("${openapi.service.url}") String url) {
        return new OpenAPI()
                .servers(List.of(new Server().url(url)))
                .info(new Info().title(serviceTitle).version(serviceVersion));
    }
//    @Bean
//    public OpenAPI customOpenAPI() {
//        return new OpenAPI()
//                .info(new Info()
//                        .title("Spring Boot 3 API")
//                        .version("1.0")
//                        .description("Hospital Management")
//                        .contact(new Contact()
//                                .name("javenock")
//                                .email("simiyuenock1990@gmail.com")
//                                .url("https://javenock-portifolio.netlify.app")));
//    }
//
//    @Bean
//    public GroupedOpenApi allApi() {
//        String[] paths = {"/**"};
//        return GroupedOpenApi.builder().group("all").pathsToMatch(paths)
//                .build();
//    }
}
