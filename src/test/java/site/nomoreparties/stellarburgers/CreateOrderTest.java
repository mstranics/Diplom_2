package site.nomoreparties.stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;


public class CreateOrderTest {


    private Order order;
    private OrderClient orderClient;
    private User user;
    private UserClient userClient;
    private String accessToken;
    private IngredientsClient ingredientsClient;
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

    @Test
    @DisplayName("Creation of Order with  authorization")
    public void orderCreatedWithAuth() {


        ValidatableResponse createResponse = orderClient.create(order,accessToken);
        assertEquals("коды ответов не совпадают", SC_OK, createResponse.extract().statusCode());

    }

    @Test
    @DisplayName("Creation of Order with no authorization")
    public void orderCreatedWithoutAuth() {


        ValidatableResponse createResponse = orderClient.create(order);
        assertEquals("коды ответов не совпадают", SC_OK, createResponse.extract().statusCode());

    }


}
