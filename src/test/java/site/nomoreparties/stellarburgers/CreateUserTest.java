package site.nomoreparties.stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import static org.apache.http.HttpStatus.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreateUserTest {

    private User user;
    private UserClient userClient;
    private String accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserHelper.addUser();
    }

    @After
    public void cleanUP() {
        userClient.delete(accessToken);
    }

    @Test
    @DisplayName("Creation of user")
    public void userCreated() {
        ValidatableResponse createResponse = userClient.create(user);
        assertEquals("коды ответов не совпадают", SC_OK, createResponse.extract().statusCode());
        assertEquals(true, createResponse.extract().path("success"));
        accessToken = createResponse.extract().path("accessToken");
    }

    @Test
    @DisplayName("Creation of the same user")
    public void sameUserNotCreated() {
        ValidatableResponse createResponse = userClient.create(user);
        ValidatableResponse createResponseNeg = userClient.create(user);
        assertEquals("коды ответов не совпадают", SC_FORBIDDEN, createResponseNeg.extract().statusCode());
        assertEquals(false, createResponseNeg.extract().path("success"));
        accessToken = createResponse.extract().path("accessToken");
    }

}
