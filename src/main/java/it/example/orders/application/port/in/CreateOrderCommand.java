package it.example.orders.application.port.in;

import java.math.BigDecimal;

public record CreateOrderCommand(String product, int quantity, BigDecimal unitPrice, String currency) {
}