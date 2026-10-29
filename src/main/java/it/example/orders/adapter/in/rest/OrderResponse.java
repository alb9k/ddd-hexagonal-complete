package it.example.orders.adapter.in.rest;

import it.example.orders.domain.Order;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderResponse(UUID id, String product, int quantity, BigDecimal total, String currency, String status) {
    public static OrderResponse from(Order o) {
        return new OrderResponse(o.id(), o.product(), o.quantity(), o.total().amount(),
                o.total().currency().getCurrencyCode(), o.status().name());
    }
}