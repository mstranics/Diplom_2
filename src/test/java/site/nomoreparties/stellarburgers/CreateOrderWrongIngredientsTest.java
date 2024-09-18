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
import static org.apache.http.HttpStatus.*;
@RunWith(Parameterized.class)
public class CreateOrderWrongIngredientsTest {


    public CreateOrderWrongIngredientsTest(List<String> ingredients, int exCode) {
        this.ingredients = ingredients;
        this.exCode = exCode;
    }

    private List<String> ingredients;
    private int exCode;


    @Parameterized.Parameters
    public static Object[][] getIngridientsData() {
        return new Object[][]{

                {List.of("someId"), SC_INTERNAL_SERVER_ERROR},
                {null, SC_BAD_REQUEST},


        };
    }

    private User user;
    private UserClient userClient;
    private IngredientsClient ingredientsClient;
    private String accessToken;
    private Order order;
    private OrderClient orderClient;
    private static List<String> ingredientsList;

    @Before
    public void setUp() {
        ingredientsClient= new IngredientsClient();
        ValidatableResponse listResponse =ingredientsClient.list();
        ingredientsList=listResponse.extract().jsonPath().getList("data._id");
        userClient = new UserClient();
        user = UserHelper.addUser();
        ValidatableResponse createResponse = userClient.create(user);
        accessToken = createResponse.extract().path("accessToken");
        orderClient = new OrderClient();
        order = OrderHelper.addOrder(ingredientsList);
    }

    @After
    public void cleanUP() {
        userClient.delete(accessToken);
    }


    @Test
    @DisplayName("Creation of Order with authorization and different ingredients")
    public void orderCreatedWithDifferentIngs() {
        order.setIngredients(ingredients);

        ValidatableResponse createResponse = orderClient.create(order, accessToken);
        assertEquals("коды ответов не совпадают", exCode, createResponse.extract().statusCode());

    }


}
