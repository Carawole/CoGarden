package learn.models;

import java.math.BigDecimal;
import java.util.Objects;

public class CartItem {

    private int cartItemId;
    private int cartId;
    private int productId;
    private BigDecimal productPrice;
    private int quantity;

    public CartItem() {
    }

    public CartItem(int cartId, int cartItemId, int productId, BigDecimal productPrice, int quantity) {
        this.cartId = cartId;
        this.cartItemId = cartItemId;
        this.productId = productId;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(int cartItemId) {
        this.cartItemId = cartItemId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return cartItemId == cartItem.cartItemId && cartId == cartItem.cartId && productId == cartItem.productId && quantity == cartItem.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartItemId, cartId, productId, quantity);
    }
}
