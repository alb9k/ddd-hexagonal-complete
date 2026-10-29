package it.example.orders.adapter.in.rest;

import it.example.orders.application.port.in.CreateOrderCommand;
import it.example.orders.application.port.in.OrderUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderUseCase orders;

    public OrderController(OrderUseCase orders) {
        this.orders = orders;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody CreateOrderRequest request) {
        var order = orders.create(new CreateOrderCommand(request.product(), request.quantity(), request.unitPrice(), request.currency()));
        return ResponseEntity.created(URI.create("/api/v1/orders/" + order.id())).body(OrderResponse.from(order));
    }

    @GetMapping("/{id}")
    public OrderResponse find(@PathVariable UUID id) {
        return OrderResponse.from(orders.find(id));
    }

    @PostMapping("/{id}/confirm")
    public OrderResponse confirm(@PathVariable UUID id) {
        return OrderResponse.from(orders.confirm(id));
    }

    @PostMapping("/{id}/cancel")
    public OrderResponse cancel(@PathVariable UUID id) {
        return OrderResponse.from(orders.cancel(id));
    }
}