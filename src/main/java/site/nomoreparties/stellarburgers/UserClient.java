package site.nomoreparties.stellarburgers;

import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;
public class Order extends RestClient {
    private static final String REGISTER_USER_PATH ="/api/auth/register";
    private static final String LOGIN_USER_PATH ="/api/auth/login";

    private static final String USER_PATH ="/api/auth/user";
    public ValidatableResponse create (User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(REGISTER_USER_PATH)
                .then();
    }

    public ValidatableResponse login (User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(LOGIN_USER_PATH)
                .then();

    }

    public ValidatableResponse delete (User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .delete(USER_PATH)
                .then();

    }

    public ValidatableResponse edit (User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .patch(USER_PATH)
                .then();

    }

    public ValidatableResponse get (User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .get(USER_PATH)
                .then();

    }

}
