package site.nomoreparties.stellarburgers;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CreateUserTest {

    private User user;
    private UserClient userClient;
    private String accessToken;

 @Before
    public  void setUp () {
     userClient = new UserClient();
     user=UserHelper.addUser();
 }
 @After
 public  void cleanUP(){
     userClient.delete(accessToken);
 }

    @Test
    public void userCreated() {
        ValidatableResponse createResponse= userClient.create(user);
        assertEquals("коды ответов не совпадают",200, createResponse.extract().statusCode());
        assertEquals(true, createResponse.extract().path("success"));
        accessToken = createResponse.extract().path("accessToken");
    }

}
