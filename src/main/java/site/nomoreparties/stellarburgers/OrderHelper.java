package site.nomoreparties.stellarburgers;

import java.util.List;

public class OrderHelper {

    public static Order addOrder(List<String>ListOfIngredients) {
        Order order = new Order();
        order.setIngredients(List.of(ListOfIngredients.get(0)));
        return order;
    }
}
