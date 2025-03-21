package learn.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Cart {

    private int cartId;
    private int userId;
    private List<CartItem> cartItems;
    private BigDecimal total;
    private LocalDateTime createdAt;

    public Cart() {
    }

    public Cart(int cartId, int userId, List<CartItem> cartItems, BigDecimal total, LocalDateTime createdAt) {
        this.cartId = cartId;
        this.userId = userId;
        this.cartItems = cartItems;
        this.total = total;
        this.createdAt = createdAt;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void addItem(CartItem item) {
        cartItems.add(item);
    }

    public void removeItem(CartItem item) {
        cartItems.remove(item);
    }

    public void clear() {
        cartItems.clear();
    }

    public void updateItem(CartItem item) {
        for (CartItem cartItem : cartItems) {
            if (cartItem.getCartItemId() == item.getCartItemId()) {
                cartItem.setQuantity(item.getQuantity());
                break;
            }
        }
    }

    public void calculateTotal() {
        BigDecimal calculatedTotal = new BigDecimal("0");
        for (CartItem cartItem : cartItems) {
            BigDecimal productTotal = cartItem.getProductPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            calculatedTotal = calculatedTotal.add(productTotal);
        }
        setTotal(calculatedTotal);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return cartId == cart.cartId && userId == cart.userId && Objects.equals(cartItems, cart.cartItems) && Objects.equals(total, cart.total) && Objects.equals(createdAt, cart.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId, userId, cartItems, total, createdAt);
    }
}
