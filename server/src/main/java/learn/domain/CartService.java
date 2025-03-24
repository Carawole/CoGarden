package learn.domain;

import learn.data.CartItemJdbcRepository;
import learn.data.CartJdbcClientRepository;
import learn.models.CartItem;
import org.springframework.stereotype.Service;
import learn.models.Cart;

import java.util.List;

@Service
public class CartService {

    private CartJdbcClientRepository cartRepository;

    private CartItemJdbcRepository cartItemRepository;

    public CartService(CartJdbcClientRepository cartRepository, CartItemJdbcRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public Result<Cart> RetrieveCartByUserId(int userId) {
        Result<Cart> result = new Result<>();
        Cart cart = cartRepository.retrieveByUserId(userId);
        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getCartId());

        cart.setCartItems(cartItems);
        return result;
    }
}
