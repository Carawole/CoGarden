package learn;

import learn.models.Category;
import learn.models.Product;
import learn.models.User;

import java.math.BigDecimal;

public class TestHelper {

    public final static int NEXT_PRODUCT_ID = 4;

    public static User makeUser() {
        return new User(1, "test@email.com", "testPasswordHash", false);
    }

    public static Product makeProduct() {
        return new Product(1, "Test Product 1", Category.FLOWERS, "Test Description 1", "Test Cycle 1", "Test Watering 1", "Test Sunlight 1", 1, new BigDecimal("1.00"));
    }
}
