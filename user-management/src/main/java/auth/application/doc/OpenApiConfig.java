package auth.application.doc;//package auth.application.doc;
//
//import io.swagger.v3.oas.models.Components;
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.security.*;
//import io.swagger.v3.oas.models.servers.Server;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.Collections;
//import java.util.List;
//
//@Configuration
//public class OpenApiConfig {
//
//    @Bean
//    public OpenAPI customOpenAPI() {
//        Server serverLocalHost = new Server();
//        serverLocalHost.setUrl("http://localhost:8080/auth");
//
//        return new OpenAPI()
//                .components(createJwtSecurityScheme())
//                .security(createJwtSecurityRequirement())
//                .info(new Info().title("Application API Docs").description("Application API Docs in OpenAPI 3.").version("1.0"))
//                .servers(Collections.singletonList(serverLocalHost));
//    }
//
//    private Components createJwtSecurityScheme() {
//        return new Components().addSecuritySchemes("Bearer Token", createJwtScheme());
//    }
//
//    private List<SecurityRequirement> createJwtSecurityRequirement() {
//        return Collections.singletonList(new SecurityRequirement().addList("Bearer Token"));
//    }
//
//    private SecurityScheme createJwtScheme() {
//        return new SecurityScheme()
//                .type(SecurityScheme.Type.HTTP)
//                .bearerFormat("JWT")
//                .scheme("bearer")
//                .description("Bearer Token");
//    }
//
//}
