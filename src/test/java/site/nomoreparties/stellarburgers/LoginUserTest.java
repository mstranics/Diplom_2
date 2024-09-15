package site.nomoreparties.stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.apache.http.HttpStatus.*;

public class LoginUserTest {

    private User user;
    private UserClient userClient;
    private String accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserHelper.addUser();
        ValidatableResponse createResponse = userClient.create(user);
        accessToken = createResponse.extract().path("accessToken");

    }

    @After
    public void cleanUP() {
        userClient.delete(accessToken);
    }

    @Test
    @DisplayName("Login of User")
    public void userLogged() {
        ValidatableResponse loginResponse = userClient.login(UserCreds.from(user));
        assertEquals("коды ответов не совпадают", SC_OK , loginResponse.extract().statusCode());
        assertEquals(true, loginResponse.extract().path("success"));

    }

    @Test
    @DisplayName("User can't login with wrong email")
    public void userNotLoggedWrongEmail() {
        user.setEmail("email@example.com");
        ValidatableResponse loginResponse = userClient.login(UserCreds.from(user));
        assertEquals("коды ответов не совпадают", SC_UNAUTHORIZED, loginResponse.extract().statusCode());
        assertEquals(false, loginResponse.extract().path("success"));
    }

    @Test
    @DisplayName("User can't login with wrong password")
    public void userNotLoggedWrongPass() {
        user.setPassword("somepass");
        ValidatableResponse loginResponse = userClient.login(UserCreds.from(user));
        assertEquals("коды ответов не совпадают", SC_UNAUTHORIZED, loginResponse.extract().statusCode());
        assertEquals(false, loginResponse.extract().path("success"));
    }
}
