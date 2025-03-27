package learn.domain;

import learn.data.OrderJdbcClientRepository;
import learn.models.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private OrderJdbcClientRepository orderRepository;

    public OrderService(OrderJdbcClientRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Result<List<Order>> findByUserId(int userId) {
        Result<List<Order>> result = new Result<>();

        if (userId <= 0) {
            result.addErrorMessage("User ID must be greater than 0.", ResultType.INVALID);
            return result;
        }

        result.setPayload(orderRepository.findByUserId(userId));
        if (result.getPayload().isEmpty()) {
            result.addErrorMessage("User has no orders.", ResultType.NOT_FOUND);
        }
        return result;
    }

    public Result<Order> findByOrderId(int orderId) {
        Result<Order> result = new Result<>();

        if (orderId <= 0) {
            result.addErrorMessage("Order ID must be greater than 0.", ResultType.INVALID);
            return result;
        }

        result.setPayload(orderRepository.findByOrderId(orderId));
        if (result.getPayload() == null) {
            result.addErrorMessage("Order not found.", ResultType.NOT_FOUND);
        }
        return result;
    }

    public boolean updateStatus(Order order) {

        if (order.getOrderId() <= 0) {
            return false;
        }

        if (order.getStatus() == null) {
            return false;
        }

        Order existingOrder = orderRepository.findByOrderId(order.getOrderId());

        if (existingOrder.getStatus() == order.getStatus()) {
            return false;
        }
        
        return orderRepository.updateStatus(order);
    }
}
