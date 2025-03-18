package learn;

import learn.models.User;

public class TestHelper {

    public static User makeUser() {
        return new User(1, "test@email.com", "testPasswordHash", false);
    }
}
