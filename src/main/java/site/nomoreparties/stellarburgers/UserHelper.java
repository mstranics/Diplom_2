package site.nomoreparties.stellarburgers;

import com.github.javafaker.Faker;

public class UserHelper {

    public static User addUser() {
        Faker faker = new Faker();
        User user = new User();
        user.setEmail(faker.internet().emailAddress());
        user.setName(faker.name().username());
        user.setPassword(faker.internet().password());
        return user;
    }

}
