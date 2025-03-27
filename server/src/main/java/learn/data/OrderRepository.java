package learn.data;

import learn.models.Order;

import java.util.List;

public interface OrderRepository {

    public List<Order> findAll();

    public Order findByOrderId(int orderId);

    public List<Order> findByUserId(int UserId);

    public boolean update(Order order);
}
