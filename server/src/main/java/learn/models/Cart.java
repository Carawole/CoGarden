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

    public Cart(int cartId, int userId, BigDecimal total, LocalDateTime createdAt) {
        this.cartId = cartId;
        this.userId = userId;
        this.total = total;
        this.createdAt = createdAt;
    }

    public Cart(int cartId, List<CartItem> cartItems, LocalDateTime createdAt, BigDecimal total, int userId) {
        this.cartId = cartId;
        this.cartItems = cartItems;
        this.createdAt = createdAt;
        this.total = total;
        this.userId = userId;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
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

    public void calculateTotal(List<CartItem> cartItems) {
        BigDecimal calculatedTotal = new BigDecimal("0.00");
        for (CartItem cartItem : cartItems) {
            BigDecimal productTotal = cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            calculatedTotal = calculatedTotal.add(productTotal);
        }
        setTotal(calculatedTotal);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return cartId == cart.cartId && userId == cart.userId && Objects.equals(createdAt, cart.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId, userId, createdAt);
    }
}
