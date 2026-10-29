package it.example.orders.application;

import it.example.orders.application.port.in.CreateOrderCommand;
import it.example.orders.application.port.in.OrderUseCase;
import it.example.orders.application.port.out.OrderRepository;
import it.example.orders.domain.Money;
import it.example.orders.domain.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Currency;
import java.util.UUID;

@Service
public class OrderService implements OrderUseCase {
    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Order create(CreateOrderCommand c) {
        Order order = Order.create(c.product(), c.quantity(), new Money(c.unitPrice(), Currency.getInstance(c.currency())));
        return repository.save(order);
    }

    @Transactional(readOnly = true)
    public Order find(UUID id) {
        return repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }

    @Transactional
    public Order confirm(UUID id) {
        Order order = find(id);
        order.confirm();
        return repository.save(order);
    }

    @Transactional
    public Order cancel(UUID id) {
        Order order = find(id);
        order.cancel();
        return repository.save(order);
    }
}