package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.valueobject.Money;
import com.algaworks.algashop.ordering.domain.valueobject.ProductName;
import com.algaworks.algashop.ordering.domain.valueobject.Quantity;
import com.algaworks.algashop.ordering.domain.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.domain.valueobject.id.ProductId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

class OrderTest {

    @Test
    public void shouldGenerate() {
        Order order = Order.draft(new CustomerId());
    }

    @Test
    public void shouldAddItem() {
        Order order = Order.draft(new CustomerId());
        ProductId productId = new ProductId();

        order.addItem(
                productId,
                new ProductName("Mouse pad"),
                new Money("100"),
                new Quantity(1)
        );

        Assertions.assertThat(order.items().size()).isEqualTo(1);

        OrderItem orderItem = order.items().iterator().next();

        Assertions.assertWith(orderItem,
                (i) -> Assertions.assertThat(i.id()).isNotNull(),
                (i) -> Assertions.assertThat(i.productName()).isEqualTo(new ProductName("Mouse pad")),
                (i) -> Assertions.assertThat(i.productId()).isEqualTo(productId),
                (i) -> Assertions.assertThat(i.price()).isEqualTo(new Money("100")),
                (i) -> Assertions.assertThat(i.quantity()).isEqualTo(new Quantity(1))
        );
    }

    @Test
    public void shouldGenerateExceptionWhenTryToChangeItemSet() {
        Order order = Order.draft(new CustomerId());
        ProductId productId = new ProductId();

        order.addItem(
                productId,
                new ProductName("Mouse pad"),
                new Money("100"),
                new Quantity(1)
        );

        Set<OrderItem> items = order.items();

        Assertions.assertThatExceptionOfType(UnsupportedOperationException.class)
                .isThrownBy(items::clear);
    }

    @Test
    public void shouldCalculateTotals() {
        Order order = Order.draft(new CustomerId());
        ProductId productId = new ProductId();

        order.addItem(
                productId,
                new ProductName("Mouse pad"),
                new Money("100"),
                new Quantity(2)
        );

        order.addItem(
                productId,
                new ProductName("RAM Memory"),
                new Money("50"),
                new Quantity(1)
        );

        Assertions.assertThat(order.totalAmount()).isEqualTo(new Money("250"));
        Assertions.assertThat(order.totalItems()).isEqualTo(new Quantity(3));
    }

}