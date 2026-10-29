package it.example.orders.application.port.out;

import it.example.orders.domain.Order;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    Order save(Order order);

    Optional<Order> findById(UUID id);
}