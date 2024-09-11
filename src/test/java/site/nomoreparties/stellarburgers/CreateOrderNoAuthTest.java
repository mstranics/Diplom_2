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


public class CreateOrderNOoAuthTest {



    private Order order;
    private OrderClient orderClient;

 @Before
    public  void setUp () {

     orderClient= new OrderClient();
     order=OrderHelper.addOrder();
 }

    @Test
    @DisplayName("Creation of Order with no authorization")
    public void createOrderNoAuth() {


        ValidatableResponse createResponse= orderClient.create(order);
        assertEquals("коды ответов не совпадают",200, createResponse.extract().statusCode());

    }



}
