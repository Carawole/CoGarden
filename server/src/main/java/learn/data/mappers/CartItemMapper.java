package learn.data.mappers;

import learn.data.ProductMapper;
import learn.models.CartItem;
import learn.models.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CartItemMapper implements RowMapper<CartItem> {
    @Override
    public CartItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        CartItem cartItem = new CartItem();
        cartItem.setCartItemId(rs.getInt("cart_item_id"));
        cartItem.setCartId(rs.getInt("cart_id"));

        ProductMapper productMapper= new ProductMapper();
        Product product = productMapper.mapRow(rs, rowNum);
        cartItem.setProduct(product);

        cartItem.setQuantity(rs.getInt("quantity"));
        return cartItem;
    }
}
