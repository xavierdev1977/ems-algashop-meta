package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.exception.OrderCannotBeEditedException;
import com.algaworks.algashop.ordering.domain.exception.OrderDoesNotContainOrderItemException;
import com.algaworks.algashop.ordering.domain.valueobject.Money;
import com.algaworks.algashop.ordering.domain.valueobject.Quantity;
import com.algaworks.algashop.ordering.domain.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.domain.valueobject.id.OrderItemId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderRemoveItemTest {

    @Test
    void givenDraftOrder_whenRemoveItem_shouldRecalculate() {
        Order order = Order.draft(new CustomerId());

        order.addItem(
                ProductTestDataBuilder.aProduct().build(),
                new Quantity(2)
        );

        OrderItem orderItem1 = order.items().iterator().next();

        order.addItem(
                ProductTestDataBuilder.aProductAltRamMemory().build(),
                new Quantity(3)
        );

        order.removeItem(orderItem1.id());

        Assertions.assertWith(order,
                (i) -> Assertions.assertThat(i.totalAmount()).isEqualTo(new Money("600.00")),
                (i) -> Assertions.assertThat(i.totalItems()).isEqualTo(new Quantity(3))
        );
    }

    @Test
    void givenDraftOrder_whenTryToRemoveNoExistingItem_shouldGenerateException() {
        Order order = OrderTestDataBuilder.anOrder().build();

        Assertions.assertThatExceptionOfType(OrderDoesNotContainOrderItemException.class)
                .isThrownBy(()-> order.removeItem(new OrderItemId()));

        Assertions.assertWith(order,
                (i) -> Assertions.assertThat(i.totalAmount()).isEqualTo(new Money("6210.00")),
                (i) -> Assertions.assertThat(i.totalItems()).isEqualTo(new Quantity(3))
        );
    }

    @Test
    void givenPlacedOrder_whenTryToRemoveItem_shouldGenerateException() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();

        Assertions.assertThatExceptionOfType(OrderCannotBeEditedException.class)
                .isThrownBy(()->order.removeItem(new OrderItemId()));

        Assertions.assertWith(order,
                (i) -> Assertions.assertThat(i.totalAmount()).isEqualTo(new Money("6210.00")),
                (i) -> Assertions.assertThat(i.totalItems()).isEqualTo(new Quantity(3))
        );
    }

}