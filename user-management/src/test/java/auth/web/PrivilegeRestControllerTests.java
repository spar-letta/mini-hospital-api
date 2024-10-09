package auth.web;

import auth.AuthApplicationTests;
import auth.dto.request.PrivilegeDto;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class PrivilegeRestControllerTests extends AuthApplicationTests {

    @Test
    public void testCreatePrivilege() {
        PrivilegeDto privilegeDto = new PrivilegeDto();
        privilegeDto.setName("READ_PRIVILEGE");
        String token = getToken();


        given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body(privilegeDto).log().all()
                .post("/privileges")
                .then().log().all()
                .statusCode(200);

        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .get("/privileges")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    public void testReadPrivilege() {
        String token = getToken();
        given()
                .header("Authorization", "Bearer " + token)
                .get("/privileges")
                .then().log().all()
                .statusCode(200);
    }
}
