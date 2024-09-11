package site.nomoreparties.stellarburgers;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestClient {
    private static final String ORDER_PATH = "/api/orders";

    @Step("Send POST request  to " + ORDER_PATH)
    public ValidatableResponse create(Order order, String accessToken) {
        return given().filter(new AllureRestAssured())
                .spec(getBaseSpec())
                .headers("Authorization", accessToken)
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then();
    }

    @Step("Send POST request without authorization to " + ORDER_PATH)
    public ValidatableResponse create(Order order) {
        return given().filter(new AllureRestAssured())
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then();
    }

    @Step("Send GET request   to " + ORDER_PATH)
    public ValidatableResponse get(String accessToken) {
        return given().filter(new AllureRestAssured())
                .spec(getBaseSpec())
                .headers("Authorization", accessToken)
                .when()
                .get(ORDER_PATH)
                .then();
    }

    @Step("Send GET request without authorization to " + ORDER_PATH)
    public ValidatableResponse get() {
        return given().filter(new AllureRestAssured())
                .spec(getBaseSpec())
                .when()
                .get(ORDER_PATH)
                .then();
    }

}
