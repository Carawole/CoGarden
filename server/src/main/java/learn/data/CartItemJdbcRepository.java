package learn.data;

import learn.data.mappers.CartItemMapper;
import learn.models.CartItem;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartItemJdbcRepository implements CartItemRepository{

    JdbcClient client;

    public CartItemJdbcRepository(JdbcClient client) {
        this.client = client;
    }

    @Override
    public CartItem add(CartItem cartItem) {

        final String sql = """
                INSERT INTO cart_item (cart_id, product_id, quantity)
                VALUES
                (:cart_id, :product_id, :quantity);
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = client.sql(sql)
                .param("cart_id", cartItem.getCartId())
                .param("product_id", cartItem.getProduct().getProductId())
                .param("quantity", cartItem.getQuantity())
                .update(keyHolder, "cart_item_id");

        if (rowsAffected <= 0) {
            return null;
        }

        cartItem.setCartItemId(keyHolder.getKey().intValue());
        return cartItem;
    }

    @Override
    public boolean clearCart(int cartId) {

        final String sql = """
                DELETE FROM cart_item
                WHERE cart_id = ?;
                """;

        return client.sql(sql)
                .param(cartId)
                .update() > 0;
    }

    @Override
    public List<CartItem> findByCartId(int cartId) {

        final String sql = """
                SELECT ci.cart_item_id, ci.cart_id, ci.product_id, ci.quantity,
                product_name, category, `description`, cycle, watering, sunlight, hardiness_zone, price
                FROM cart_item ci
                JOIN product p ON ci.product_id = p.product_id
                WHERE ci.cart_id = ?;
                """;

        return client.sql(sql)
                .param(cartId)
                .query(new CartItemMapper())
                .list();
    }

    @Override
    public boolean remove(int cartItemId) {

        final String sql = """
                DELETE FROM cart_item
                WHERE cart_item_id = :cart_item_id;
                """;

        return client.sql(sql)
                .param("cart_item_id",cartItemId)
                .update() > 0;
    }

    @Override
    public boolean updateQuantity(CartItem cartItem) {

        final String sql = """
                UPDATE cart_item
                SET quantity = :quantity
                WHERE cart_item_id = :cart_item_id;
                """;

        return client.sql(sql)
                .param("quantity",cartItem.getQuantity())
                .param("cart_item_id",cartItem.getCartItemId())
                .update() > 0;
    }
}
