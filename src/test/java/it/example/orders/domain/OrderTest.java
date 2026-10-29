package it.example.orders.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderTest {
    @Test
    void confirmsCreatedOrderAndRejectsSecondConfirmation() {
        var order = Order.create("Book", 2, new Money(new BigDecimal("10.50"), Currency.getInstance("EUR")));
        assertEquals(new BigDecimal("21.00"), order.total().amount());
        order.confirm();
        assertEquals(OrderStatus.CONFIRMED, order.status());
        assertThrows(IllegalStateException.class, order::confirm);
    }

    @Test
    void rejectsNegativeMoney() {
        assertThrows(IllegalArgumentException.class,
                () -> new Money(new BigDecimal("-1"), Currency.getInstance("EUR")));
    }
}