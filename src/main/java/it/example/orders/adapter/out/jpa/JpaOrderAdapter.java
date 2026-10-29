package it.example.orders.adapter.out.jpa;

import it.example.orders.application.port.out.OrderRepository;
import it.example.orders.domain.Money;
import it.example.orders.domain.Order;
import it.example.orders.domain.OrderStatus;
import org.springframework.stereotype.Repository;

import java.util.Currency;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaOrderAdapter implements OrderRepository {
    private final SpringDataOrderRepository jpa;

    public JpaOrderAdapter(SpringDataOrderRepository jpa) {
        this.jpa = jpa;
    }

    public Order save(Order order) {
        OrderEntity e = new OrderEntity(order.id(), order.product(), order.quantity(), order.total().amount(),
                order.total().currency().getCurrencyCode(), order.status().name());
        return toDomain(jpa.save(e));
    }

    public Optional<Order> findById(UUID id) {
        return jpa.findById(id).map(this::toDomain);
    }

    private Order toDomain(OrderEntity e) {
        return Order.restore(e.id, e.product, e.quantity, new Money(e.total, Currency.getInstance(e.currency)), OrderStatus.valueOf(e.status));
    }
}