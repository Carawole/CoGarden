package learn.data;

import learn.data.mappers.CartMapper;
import learn.models.Cart;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class CartJdbcClientRepository implements CartRepository {

    private JdbcClient client;

    public CartJdbcClientRepository(JdbcClient client) {
        this.client = client;
    }

    @Override
    @Transactional
    public boolean submitOrder(Cart cart) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        client.sql("""
                INSERT INTO `order` (user_id, total)
                SELECT user_id, total FROM cart
                WHERE cart_id = :cartId
                """).param("cartId", cart.getCartId()).update(keyHolder, "order_id");

        int orderId = keyHolder.getKey().intValue();

        client.sql("""
                INSERT INTO order_item (order_id, product_id, quantity)
                SELECT :orderId, product_id, quantity FROM cart_item
                WHERE cart_id = :cartId
                """).param("orderId", orderId).param("cartId", cart.getCartId()).update();

        return client.sql("""
                DELETE FROM cart
                WHERE cart_id = :cartId
                """).param("cartId", cart.getCartId()).update() > 0;
    }

    @Override
    public List<Cart> findAll() {
        final String sql = """
                SELECT cart_id, user_id, total, created_at
                FROM cart
                """;

        return client.sql(sql).query(new CartMapper()).list();
    }

    @Override
    public Cart findByCartId(int cartId) {

        final String sql = """
                SELECT cart_id, user_id, total, created_at
                FROM cart
                WHERE cart_id = :cartId
                """;

        return client.sql(sql).param("cartId", cartId).query(new CartMapper()).optional().orElse(null);
    }

    @Override
    @Transactional
    public Cart retrieveByUserId(int userId) {

        String cartCheck = "SELECT * FROM cart WHERE user_id = :userId";

        if (client.sql(cartCheck)
                .param("userId", userId)
                .query(new CartMapper())
                .optional().orElse(null)
                == null) {
            String createCart = "INSERT INTO cart (user_id, total) VALUES (:userId, :total)";

            client.sql(createCart)
                    .param("userId", userId)
                    .param("total", 0)
                    .update();
        }

        return client.sql(cartCheck)
                .param("userId", userId)
                .query(new CartMapper())
                .optional().orElse(null);

//        final String sql = "SELECT * FROM cart WHERE user_id = :userId";
//
//        return client.sql(sql)
//                .param("userId", userId)
//                .query(new CartMapper())
//                .optional().orElse(null);
    }

    @Override
    public boolean updateTotal(Cart cart) {

        final String sql = "UPDATE cart SET total = :total WHERE cart_id = :cartId";

        return client.sql(sql)
                .param("total", cart.getTotal())
                .param("cartId", cart.getCartId())
                .update() > 0;
    }
}
