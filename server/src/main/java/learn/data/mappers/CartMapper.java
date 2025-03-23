package learn.data.mappers;

import learn.models.Cart;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CartMapper implements RowMapper<Cart> {
    @Override
    public Cart mapRow(ResultSet rs, int rowNum) throws SQLException {
        Cart cart = new Cart();
        cart.setCartId(rs.getInt("cart_id"));
        cart.setUserId(rs.getInt("user_id"));
        cart.setTotal(rs.getBigDecimal("total"));
        cart.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return cart;
    }
}
