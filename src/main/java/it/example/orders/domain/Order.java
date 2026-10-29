package it.example.orders.domain;

import java.util.Objects;
import java.util.UUID;

public final class Order {
    private final UUID id;
    private final String product;
    private final int quantity;
    private final Money total;
    private OrderStatus status;

    private Order(UUID id, String product, int quantity, Money total, OrderStatus status) {
        this.id = Objects.requireNonNull(id);
        this.product = Objects.requireNonNull(product);
        if (product.isBlank()) throw new IllegalArgumentException("Product is required");
        if (quantity < 1) throw new IllegalArgumentException("Quantity must be positive");
        this.quantity = quantity;
        this.total = Objects.requireNonNull(total);
        this.status = Objects.requireNonNull(status);
    }

    public static Order create(String product, int quantity, Money unitPrice) {
        if (quantity < 1) throw new IllegalArgumentException("Quantity must be positive");
        return new Order(UUID.randomUUID(), product, quantity, unitPrice.multiply(quantity), OrderStatus.CREATED);
    }

    public static Order restore(UUID id, String product, int quantity, Money total, OrderStatus status) {
        return new Order(id, product, quantity, total, status);
    }

    public void confirm() {
        if (status != OrderStatus.CREATED) throw new IllegalStateException("Only CREATED orders can be confirmed");
        status = OrderStatus.CONFIRMED;
    }

    public void cancel() {
        if (status != OrderStatus.CREATED) throw new IllegalStateException("Only CREATED orders can be cancelled");
        status = OrderStatus.CANCELLED;
    }

    public UUID id() {
        return id;
    }

    public String product() {
        return product;
    }

    public int quantity() {
        return quantity;
    }

    public Money total() {
        return total;
    }

    public OrderStatus status() {
        return status;
    }
}