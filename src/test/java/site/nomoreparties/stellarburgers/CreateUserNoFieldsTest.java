package site.nomoreparties.stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import static org.apache.http.HttpStatus.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CreateUserNoFieldsTest {
    public CreateUserNoFieldsTest(String email, String password, String name, int exCode, String exText, boolean success) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.exCode = exCode;
        this.exText = exText;
        this.success = success;
    }

    private final String email;
    private final String password;
    private final String name;
    private final int exCode;
    private final String exText;
    private final boolean success;


    @Parameterized.Parameters
    public static Object[][] getUserData() {
        return new Object[][]{
                {"", "midepass", "midename", SC_FORBIDDEN, "Email, password and name are required fields", false},
                {null, "midepass", "midename", SC_FORBIDDEN, "Email, password and name are required fields", false},
                {"email@example.com", "", "midename", SC_FORBIDDEN, "Email, password and name are required fields", false},
                {"email@example.com", null, "midename", SC_FORBIDDEN, "Email, password and name are required fields", false},
                {"email@example.com", "midepass", "", SC_FORBIDDEN, "Email, password and name are required fields", false},
                {"email@example.com", "midepass", null, SC_FORBIDDEN, "Email, password and name are required fields", false},

        };
    }

    private User user;
    private UserClient userClient;
    private String accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserHelper.addUser();
    }


    @Test
    @DisplayName("Creation of user without mandatory fields")
    public void createUserNoRequiredFields() {
        user.setEmail(email);
        user.setName(name);
        user.setPassword(password);
        ValidatableResponse createResponse = userClient.create(user);
        assertEquals("коды ответов не совпадают", exCode, createResponse.extract().statusCode());
        assertEquals("значения в success не совпадают", success, createResponse.extract().path("success"));
        assertEquals("текст ошибки не совпадает", exText, createResponse.extract().path("message"));

    }


}
