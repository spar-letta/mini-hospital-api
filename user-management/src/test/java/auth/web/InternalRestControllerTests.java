package auth.web;

import auth.AuthApplicationTests;
import org.junit.Test;

import static io.restassured.RestAssured.given;


public class InternalRestControllerTests extends AuthApplicationTests {

    @Test
    public void testGetUser() {
        given()
                .queryParam("username", "123456")
                .get("/internal/user")
                .then().log().all()
                .statusCode(200);
    }
}
