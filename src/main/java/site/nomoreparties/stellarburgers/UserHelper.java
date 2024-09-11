package site.nomoreparties.stellarburgers;

import com.github.javafaker.Faker;

public class UserHelper {

    public static User addUser() {
        Faker faker = new Faker();
        User user = new User();
        user.email = faker.internet().emailAddress();
        user.name = faker.name().username();
        user.password = faker.internet().password();
        return user;
    }

}
