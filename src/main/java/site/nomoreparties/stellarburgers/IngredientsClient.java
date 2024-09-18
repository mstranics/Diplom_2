package site.nomoreparties.stellarburgers;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class IngredientsClient extends RestClient {
    private static final String INGREDIENTS_PATH = "/api/ingredients";

    @Step("Send GET request  to " + INGREDIENTS_PATH)
    public ValidatableResponse list () {
        return given().filter(new AllureRestAssured())
                .spec(getBaseSpec())
                .when()
                .get(INGREDIENTS_PATH)
                .then();
    }


}
