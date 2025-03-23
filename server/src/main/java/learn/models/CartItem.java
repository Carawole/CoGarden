package learn.models;

import java.math.BigDecimal;
import java.util.Objects;

public class CartItem {

    private int cartItemId;
    private int cartId;
    private Product product;
    private int quantity;

    public CartItem() {
    }

    public CartItem(int cartItemId, int cartId, Product product, int quantity) {
        this.cartItemId = cartItemId;
        this.cartId = cartId;
        this.product = product;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(int cartItemId) {
        this.cartItemId = cartItemId;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return cartItemId == cartItem.cartItemId && cartId == cartItem.cartId && quantity == cartItem.quantity && Objects.equals(product, cartItem.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartItemId, cartId, product, quantity);
    }
}
