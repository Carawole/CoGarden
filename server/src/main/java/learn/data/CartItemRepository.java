package learn.data;

import learn.models.CartItem;

import java.util.List;

public interface CartItemRepository {

    public List<CartItem> findByCartId(int cartId);

    public CartItem add(CartItem cartItem);

    public boolean updateQuantity(CartItem cartItem);

    public boolean remove(int cartItemId);

    public boolean clearCart(int cartId);
}
