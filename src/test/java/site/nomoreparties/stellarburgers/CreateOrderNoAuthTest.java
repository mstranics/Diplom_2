package site.nomoreparties.stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import static org.apache.http.HttpStatus.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class CreateOrderNoAuthTest {


    private Order order;
    private OrderClient orderClient;

    @Before
    public void setUp() {

        orderClient = new OrderClient();
        order = OrderHelper.addOrder();
    }

    @Test
    @DisplayName("Creation of Order with no authorization")
    public void createOrderNoAuth() {


        ValidatableResponse createResponse = orderClient.create(order);
        assertEquals("коды ответов не совпадают", SC_OK, createResponse.extract().statusCode());

    }


}
