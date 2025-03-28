package learn.data;

import learn.models.CartItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.List;

import static learn.TestHelper.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CartItemJdbcRepositoryTest {

    @Autowired
    JdbcClient client;

    @Autowired
    CartItemJdbcRepository cartItemRepository;

//    @Autowired
//    OrderItemJdbcRepository orderItemRepository;

    @BeforeEach
    void setUp() {
        client.sql("call set_known_good_state();").update();
    }

    @Test
    void shouldFindItemsByCartId() {
        List<CartItem> items = cartItemRepository.findByCartId(1);

        assertEquals(2, items.size());
    }

    @Test
    void shouldAddItem() {
        CartItem item = makeCartItem();
        item.setCartItemId(0);

        CartItem actual = cartItemRepository.add(item);

        assertNotNull(actual);
        assertEquals(3, actual.getCartItemId());
    }

    @Test
    void shouldUpdateItemQuantity() {
        CartItem item = makeCartItem();
        item.setCartItemId(0);

        CartItem actual = cartItemRepository.add(item);

        actual.setQuantity(5);

        boolean result = cartItemRepository.updateQuantity(actual);

        assertTrue(result);
    }

    @Test
    void shouldRemoveItem() {
        boolean result = cartItemRepository.remove(1);

        assertTrue(result);
    }

}