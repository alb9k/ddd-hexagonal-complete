package it.example.orders.application.port.in;

import it.example.orders.domain.Order;

import java.util.UUID;

public interface OrderUseCase {
    Order create(CreateOrderCommand command);

    Order find(UUID id);

    Order confirm(UUID id);

    Order cancel(UUID id);
}