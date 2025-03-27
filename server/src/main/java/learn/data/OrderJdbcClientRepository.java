package learn.data;

import learn.data.mappers.OrderMapper;
import learn.models.Order;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderJdbcClientRepository implements OrderRepository{
    private JdbcClient client;

    public OrderJdbcClientRepository(JdbcClient client) {
        this.client = client;
    }

    @Override
    public List<Order> findAll() {
        final String sql = """
                SELECT order_id, user_id, order_status, total, created_at
                FROM `order`;
                """;

        return client.sql(sql)
                .query(new OrderMapper())
                .list();
    }

    @Override
    public List<Order> findByUserId(int UserId) {
        final String sql = """
                SELECT order_id, user_id, order_status, total, created_at
                FROM `order`
                WHERE user_id = :userId;
                """;

        return client.sql(sql)
                .param("userId", UserId)
                .query(new OrderMapper())
                .list();
    }

    @Override
    public Order findByOrderId(int orderId) {
        final String sql = """
                SELECT order_id, user_id, order_status, total, created_at
                FROM `order`
                WHERE order_id = :orderId;
                """;

        return client.sql(sql)
                .param("orderId", orderId)
                .query(new OrderMapper())
                .optional()
                .orElse(null);
    }

    @Override
    public boolean update(Order order) {
        final String sql = """
                UPDATE `order`
                SET order_status = :orderStatus
                WHERE order_id = :orderId;
                """;

        return client.sql(sql)
                .param("orderStatus", order.getStatus().toString())
                .param("orderId", order.getOrderId())
                .update() > 0;
    }
}
