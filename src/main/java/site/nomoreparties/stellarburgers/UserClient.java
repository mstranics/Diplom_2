package site.nomoreparties.stellarburgers;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserClient extends RestClient {
    private static final String REGISTER_USER_PATH = "/api/auth/register";
    private static final String LOGIN_USER_PATH = "/api/auth/login";

    private static final String USER_PATH = "/api/auth/user";

    @Step("Send POST request to " + REGISTER_USER_PATH)
    public ValidatableResponse create(User user) {
        return given().filter(new AllureRestAssured())
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(REGISTER_USER_PATH)
                .then();
    }

    @Step("Send POST request to " + LOGIN_USER_PATH)
    public ValidatableResponse login(UserCreds userCreds) {
        return given().filter(new AllureRestAssured())
                .spec(getBaseSpec())
                .body(userCreds)
                .when()
                .post(LOGIN_USER_PATH)
                .then();

    }

    @Step("Send DELETE request to " + USER_PATH)
    public ValidatableResponse delete(String accessToken) {
        return given().filter(new AllureRestAssured())
                .spec(getBaseSpec())
                .headers("Authorization", accessToken)
                .when()
                .delete(USER_PATH)
                .then();

    }

    @Step("Send PATCH request to " + USER_PATH)
    public ValidatableResponse edit(User user, String accessToken) {
        return given().filter(new AllureRestAssured())
                .spec(getBaseSpec())
                .headers("Authorization", accessToken)
                .body(user)
                .when()
                .patch(USER_PATH)
                .then();

    }

    @Step("Send PATCH request without authorization to " + USER_PATH)
    public ValidatableResponse edit(User user) {
        return given().filter(new AllureRestAssured())
                .spec(getBaseSpec())

                .body(user)
                .when()
                .patch(USER_PATH)
                .then();

    }

    @Step("Send GET request to " + USER_PATH)
    public ValidatableResponse get(String accessToken) {
        return given().filter(new AllureRestAssured())
                .spec(getBaseSpec())
                .headers("Authorization", accessToken)
                .when()
                .get(USER_PATH)
                .then();

    }

}
