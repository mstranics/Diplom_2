package site.nomoreparties.stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.apache.http.HttpStatus.*;


public class GetUserOrdersTest {


    private User user;
    private UserClient userClient;
    private String accessToken;
    private Order order;
    private OrderClient orderClient;
    private String orderID;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserHelper.addUser();
        ValidatableResponse createResponse = userClient.create(user);
        accessToken = createResponse.extract().path("accessToken");
        orderClient = new OrderClient();
        order = OrderHelper.addOrder();
        ValidatableResponse createOrderResponse = orderClient.create(order, accessToken);
        assertEquals("коды ответов не совпадают", SC_OK, createOrderResponse.extract().statusCode());
        orderID = createOrderResponse.extract().path("order._id");
    }

    @After
    public void cleanUP() {
        userClient.delete(accessToken);
    }


    @Test
    @DisplayName("List of user's orders with auth")
    public void getUserOrders() {
        ValidatableResponse getResponse = orderClient.get(accessToken);
        assertEquals("коды ответов не совпадают", SC_OK, getResponse.extract().statusCode());
        assertEquals("", true, getResponse.extract().path("success"));
        assertEquals("", orderID, getResponse.extract().path("orders[0]._id"));

    }

    @Test
    @DisplayName("List of user's orders without auth")
    public void getUserOrdersNoAuth() {
        ValidatableResponse getResponse = orderClient.get();
        assertEquals("коды ответов не совпадают", SC_UNAUTHORIZED, getResponse.extract().statusCode());
        assertEquals("", false, getResponse.extract().path("success"));


    }


}
