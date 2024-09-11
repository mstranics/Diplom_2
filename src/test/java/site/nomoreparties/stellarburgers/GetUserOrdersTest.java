package site.nomoreparties.stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class GetOrdersTest {


    public GetOrdersTest(List<String> ingredients, int exCode) {
        this.ingredients = ingredients;
        this.exCode = exCode;
    }

    private List<String> ingredients;
    private int exCode;


    @Parameterized.Parameters
    public static Object[][] getIngridientsData() {
        return new Object[][]{
                {List.of("61c0c5a71d1f82001bdaaa6d"),200},
                {List.of("61c0c5a71d1f82001bdaaa6f","61c0c5a71d1f82001bdaaa6d"),200},
                {List.of("someId"),400},
                {null,400},


        };
    }

    private User user;
    private UserClient userClient;
    private String accessToken;
    private Order order;
    private OrderClient orderClient;

 @Before
    public  void setUp () {
     userClient = new UserClient();
     user=UserHelper.addUser();
     ValidatableResponse createResponse= userClient.create(user);
     accessToken = createResponse.extract().path("accessToken");
     orderClient= new OrderClient();
     order=OrderHelper.addOrder();
 }
    @After
    public  void cleanUP(){
        userClient.delete(accessToken);
    }


    @Test
    @DisplayName("Creation of Order with authorization and different ingredients")
    public void createOrderDiffIng() {
     order.setIngredients(ingredients);

        ValidatableResponse createResponse= orderClient.create(order,accessToken);
        assertEquals("коды ответов не совпадают",exCode, createResponse.extract().statusCode());

    }



}
