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

    public Result<List<Cart>> findAll() {
        Result<List<Cart>> result = new Result<>();
        result.setPayload(cartRepository.findAll());
        return result;
    }

    public Result<Cart> retrieveCartByUserId(int userId) {
        Result<Cart> result = new Result<>();
        Cart cart = cartRepository.retrieveByUserId(userId);
        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getCartId());
        cart.setCartItems(cartItems);
        result.setPayload(cart);
        return result;
    }

    public Result<Cart> addToCart(CartItem cartItem) {
        Result<Cart> result = new Result<>();
        cartItemRepository.add(cartItem);
        Cart preCart = cartRepository.findByCartId(cartItem.getCartId());
        Cart cart = updateTotal(preCart);
        result.setPayload(cart);
        return result;
    }

    public Result<Cart> updateCartItemQuantity(CartItem cartItem) {
        Result<Cart> result = new Result<>();

        if (cartItem.getQuantity() <= 0) {
            cartItemRepository.remove(cartItem.getCartItemId());
        } else {
            cartItemRepository.updateQuantity(cartItem);
        }
        Cart preCart = cartRepository.findByCartId(cartItem.getCartId());
        Cart cart = updateTotal(preCart);
        result.setPayload(cart);
        return result;
    }

    public Result<Cart> removeFromCart(CartItem cartItem) {
        Result<Cart> result = new Result<>();
        cartItemRepository.remove(cartItem.getCartItemId());
        Cart preCart = cartRepository.findByCartId(cartItem.getCartId());
        Cart cart = updateTotal(preCart);
        result.setPayload(cart);
        return result;
    }

    public boolean submitOrder(Cart cart) {
        return cartRepository.submitOrder(cart);
    }

    private Cart updateTotal(Cart cart) {
        cart.calculateTotal(cart.getCartItems());
        cartRepository.updateTotal(cart);
        return cart;
    }
}
