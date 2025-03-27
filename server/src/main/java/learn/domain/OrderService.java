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

    public Result<Boolean> updateStatus(Order order) {
        Result<Boolean> result = new Result<>();

        if (order.getOrderId() <= 0) {
            result.addErrorMessage("Order ID must be greater than 0.", ResultType.INVALID);
        }

        if (order.getStatus() == null) {
            result.addErrorMessage("Status is required.", ResultType.INVALID);
        }

        if (!result.isSuccess()) {
            return result;
        }

        Order existingOrder = orderRepository.findByOrderId(order.getOrderId());

        if (existingOrder.getStatus() == order.getStatus()) {
            result.addErrorMessage("Status is the same.", ResultType.INVALID);
            return result;
        }

        if (orderRepository.updateStatus(order)) {
            result.setPayload(true);
        } else {
            result.addErrorMessage("Failed to update status.", ResultType.INVALID);
        }

        return result;
    }
}
