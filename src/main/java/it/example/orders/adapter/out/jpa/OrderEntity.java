package it.example.orders.adapter.out.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    public UUID id;
    @Column(nullable = false)
    public String product;
    public int quantity;
    @Column(nullable = false, precision = 19, scale = 2)
    public BigDecimal total;
    @Column(nullable = false, length = 3)
    public String currency;
    @Column(nullable = false)
    public String status;

    protected OrderEntity() {
    }

    public OrderEntity(UUID id, String product, int quantity, BigDecimal total, String currency, String status) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.total = total;
        this.currency = currency;
        this.status = status;
    }
}