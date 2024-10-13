package auth.web;

import auth.AuthApplicationTests;
import auth.dto.request.CreateUserDTO;
import auth.dto.request.RoleDto;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Oauth2Tests extends AuthApplicationTests {
    @Test
    public void standardEndpoints() {
        given()
                .get("/.well-known/openid-configuration")
                .then().log().all()
                .statusCode(200);

        given()
                .get("/oauth2/jwks")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    public void testLogin() {
        String token = getToken();
        given()
                .header("Authorization", "Bearer " + token).log().all()
                .get("/profile")
                .then()
                .statusCode(200)
                .body("firstName", equalTo("admin"))
                .body("lastName", equalTo("admin1"))
                .body("otherName", equalTo(null))
                .body("userName", equalTo("123456"))
                .body("contactEmail", equalTo("admin@gmail.com"))
                .body("contactPhonenumber", equalTo("0708461561"))
                .body("verifiedEmail", equalTo(false))
                .body("verifiedPhonenumber", equalTo(false));

        Map<String, String> introspectPayload = new HashMap<>();
        introspectPayload.put("token", token);

        given()
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("browser-client:secret".getBytes(StandardCharsets.UTF_8)))
                .formParams(introspectPayload)
                .post("/oauth2/introspect")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    public void testGetAllRoleWorks() {
        String token = getToken();
        given()
                .header("Authorization", "Bearer " + token).log().all()
                .get("/users")
                .then().log().all()
                .statusCode(200);

    }

    @Test
    public void testGetRoleWorks() {
        String token = getToken();
        given()
                .header("Authorization", "Bearer " + token).log().all()
                .get("/users/{publicId}", "bb874ce2-dc46-4f11-8915-c1d644f236df")
                .then().log().all()
                .statusCode(200);

    }

    @Test
    public void testAddRoleToUser() {
        RoleDto roleDto = new RoleDto("ROLE_SECRETARY");
        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + getToken()).log().all()
                .body(roleDto).log().all()
                .put("/users/assignRole/{publicId}", "bb874ce2-dc46-4f11-8915-c1d644f236df")
                .then().log().all()
                .statusCode(200);
    }
}
