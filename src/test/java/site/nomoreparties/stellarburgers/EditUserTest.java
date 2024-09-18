package site.nomoreparties.stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import static org.apache.http.HttpStatus.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EditUserTest {

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
    @DisplayName("Edit user info")
    public void allUserInfoChanged() {
        user.setEmail("x" + user.getEmail());
        user.setPassword("y" + user.getPassword());
        user.setName("z" + user.getName());
        ValidatableResponse editResponse = userClient.edit(user, accessToken);
        assertEquals("коды ответов не совпадают", SC_OK, editResponse.extract().statusCode());
        assertEquals(true, editResponse.extract().path("success"));
        ValidatableResponse getResponse = userClient.get(accessToken);
        assertEquals(SC_OK, getResponse.extract().statusCode());

        assertEquals("email не поменялся", user.getEmail(), getResponse.extract().path("user.email"));
        assertEquals("name не поменялся", user.getName(), getResponse.extract().path("user.name"));
        ValidatableResponse loginResponse = userClient.login(UserCreds.from(user));
        assertEquals("кажется, не поменялся пароль", SC_OK, loginResponse.extract().statusCode());

    }

    @Test
    @DisplayName("Edit user info without auth")
    public void userInfoNotChangeWithoutAuth() {
        user.setEmail("x" + user.getEmail());
        user.setPassword("y" + user.getPassword());
        user.setName("z" + user.getName());
        ValidatableResponse editResponse = userClient.edit(user);
        assertEquals("коды ответов не совпадают", 401, editResponse.extract().statusCode());
        assertEquals(false, editResponse.extract().path("success"));
        assertEquals("текст ошибки не совпадает", "You should be authorised", editResponse.extract().path("message"));

    }

}
