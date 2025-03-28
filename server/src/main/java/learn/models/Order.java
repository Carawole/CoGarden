package learn.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Order {

    private int orderId;
    private int userId;
    private OrderStatus status;
    private List<OrderItem> orderItems;
    private BigDecimal total;
    private LocalDateTime createdAt;

    public Order() {
    }

    public Order(LocalDateTime createdAt, int userId, BigDecimal total, OrderStatus status, int orderId) {
        this.createdAt = createdAt;
        this.userId = userId;
        this.total = total;
        this.status = status;
        this.orderId = orderId;
    }

    public Order(LocalDateTime createdAt, int orderId, List<OrderItem> orderItems, OrderStatus status, BigDecimal total, int userId) {
        this.createdAt = createdAt;
        this.orderId = orderId;
        this.orderItems = orderItems;
        this.status = status;
        this.total = total;
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId && userId == order.userId && Objects.equals(total, order.total) && Objects.equals(createdAt, order.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, userId, total, createdAt);
    }
}
