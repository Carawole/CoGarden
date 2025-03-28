package learn.domain;

import learn.data.CartItemJdbcRepository;
import learn.data.CartJdbcClientRepository;
import learn.models.CartItem;
import org.springframework.stereotype.Service;
import learn.models.Cart;

import java.util.ArrayList;
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
        List<Cart> carts = cartRepository.findAll();
        for (Cart cart : carts) {
            List<CartItem> cartItems = getCartItems(cart.getCartId());
            cart.setCartItems(cartItems);
        }
        result.setPayload(carts);
        return result;
    }

    public Result<Cart> retrieveCartByUserId(int userId) {
        Result<Cart> result = new Result<>();
        Cart cart = cartRepository.retrieveByUserId(userId);
        List<CartItem> cartItems = getCartItems(cart.getCartId());
        cart.setCartItems(cartItems);
        result.setPayload(cart);
        return result;
    }

    public Result<Cart> addToCart(CartItem cartItem) {
        Result<Cart> result = validate(cartItem);

        if (!result.isSuccess()) {
            return result;
        }
        cartItemRepository.add(cartItem);
        Cart preCart = cartRepository.findByCartId(cartItem.getCartId());
        List<CartItem> cartItems = getCartItems(cartItem.getCartId());
        preCart.setCartItems(cartItems);
        Cart cart = updateTotal(preCart);
        result.setPayload(cart);
        return result;
    }

    public Result<Cart> updateCartItemQuantity(CartItem cartItem) {
        Result<Cart> result = validate(cartItem);

        if (!result.isSuccess()) {
            return result;
        }

        if (cartItem.getQuantity() <= 0) {
            cartItemRepository.remove(cartItem.getCartItemId());
        } else {
            cartItemRepository.updateQuantity(cartItem);
        }
        Cart preCart = cartRepository.findByCartId(cartItem.getCartId());
        List<CartItem> cartItems = getCartItems(cartItem.getCartId());
        preCart.setCartItems(cartItems);
        Cart cart = updateTotal(preCart);
        result.setPayload(cart);
        return result;
    }

    public Result<Cart> removeFromCart(CartItem cartItem) {
        Result<Cart> result = validate(cartItem);

        if (!result.isSuccess()) {
            return result;
        }
        cartItemRepository.remove(cartItem.getCartItemId());
        Cart preCart = cartRepository.findByCartId(cartItem.getCartId());
        List<CartItem> cartItems = getCartItems(cartItem.getCartId());
        preCart.setCartItems(cartItems);
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

    private List<CartItem> getCartItems(int cartId) {
        List<CartItem> cartItems = new ArrayList<>();
        cartItems = cartItemRepository.findByCartId(cartId);
        return cartItems;
    }

    private Result<Cart> validate(CartItem cartItem) {
        Result<Cart> result = new Result<>();
        if (cartItem == null) {
            result.addErrorMessage("CartItem cannot be null.", ResultType.INVALID);
            return result;
        }
        if (cartItem.getCartId() <= 0) {
            result.addErrorMessage("CartId is required.", ResultType.INVALID);
        }
        if (cartItem.getProduct() == null) {
            result.addErrorMessage("Product is required.", ResultType.INVALID);
        }
        return result;
    }
}
