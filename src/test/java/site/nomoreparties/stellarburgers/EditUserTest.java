package site.nomoreparties.stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoginUserTest {

    private User user;
    private UserClient userClient;
   private  String accessToken;

 @Before
    public  void setUp () {
     userClient = new UserClient();
     user=UserHelper.addUser();
     ValidatableResponse createResponse= userClient.create(user);
     accessToken = createResponse.extract().path("accessToken");

 }
 @After
 public  void cleanUP(){
     userClient.delete(accessToken);
 }

    @Test
    @DisplayName("Log in of User")
    public void userLogged() {
        ValidatableResponse loginResponse= userClient.login(UserCreds.from(user));
        assertEquals("коды ответов не совпадают",200, loginResponse.extract().statusCode());
        assertEquals(true, loginResponse.extract().path("success"));

    }
    @Test
    @DisplayName("User can't login with wrong email")
    public void userLoginWrongEmail() {
     user.setEmail("email@example.com");
     ValidatableResponse loginResponse= userClient.login(UserCreds.from(user));
        assertEquals("коды ответов не совпадают",401, loginResponse.extract().statusCode());
        assertEquals(false, loginResponse.extract().path("success"));
    }

    @Test
    @DisplayName("User can't login with wrong password")
    public void userLoginWrongPass() {
        user.setPassword("somepass");
        ValidatableResponse loginResponse= userClient.login(UserCreds.from(user));
        assertEquals("коды ответов не совпадают",401, loginResponse.extract().statusCode());
        assertEquals(false, loginResponse.extract().path("success"));
    }
}
