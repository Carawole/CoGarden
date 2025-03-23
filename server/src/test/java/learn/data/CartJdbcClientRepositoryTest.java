package learn.data;

import learn.TestHelper;
import learn.models.Cart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CartJdbcClientRepositoryTest {

    @Autowired
    JdbcClient client;

    @Autowired
    CartJdbcClientRepository cartRepository;

//    @Autowired
//    OrderJdbcClientRepository orderRepository;

    @BeforeEach
    void setup() {
        client.sql("call set_known_good_state();").update();
    }

    @Test
    void shouldFindAll() {
        List<Cart> carts = cartRepository.findAll();
        assertEquals(1, carts.size());
    }

    @Nested
    class RetrieveCartById {

        @Test
        void shouldFindExistingCart() {
            Cart cart = cartRepository.retrieveByUserId(2);

            assertNotNull(cart);
            assertEquals(new BigDecimal("1.00"), cart.getTotal());
        }

        @Test
        void shouldGenerateNewCartIfNoneExistsForUser() {
            Cart newCart = TestHelper.makeCart();
            newCart.setCartId(2);

            Cart cart = cartRepository.retrieveByUserId(1);

            assertTrue(cart.getCreatedAt().isBefore(newCart.getCreatedAt()));
            assertEquals(newCart.getTotal(), cart.getTotal());
            assertEquals(newCart.getCartId(), cart.getCartId());
        }

    }

    @Test
    void shouldUpdateCartTotal() {
        Cart cart = cartRepository.retrieveByUserId(2);
        cart.setTotal(new BigDecimal("25.00"));
         boolean result = cartRepository.updateTotal(cart);

        Cart updatedCart = cartRepository.retrieveByUserId(2);
        assertTrue(result);
        assertEquals(new BigDecimal("25.00"), updatedCart.getTotal());
    }

    // TODO: Add test for delete after adding order repository

}