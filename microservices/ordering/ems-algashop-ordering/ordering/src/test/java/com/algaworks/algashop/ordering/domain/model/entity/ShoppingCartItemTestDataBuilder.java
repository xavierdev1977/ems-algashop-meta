package com.algaworks.algashop.ordering.domain.model.entity;

import com.algaworks.algashop.ordering.domain.model.valueobject.Money;
import com.algaworks.algashop.ordering.domain.model.valueobject.ProductName;
import com.algaworks.algashop.ordering.domain.model.valueobject.Quantity;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.ProductId;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.ShoppingCartId;

public class ShoppingCartItemTestDataBuilder {

    private ShoppingCartId shoppingCartId = ShoppingCartTestDataBuilder.DEFAULT_SHOPPING_CART_ID;
    private ProductId productId = ProductTestDataBuilder.DEFAULT_PRODUCT_ID;
    private ProductName productName = new ProductName("Notebook");
    private Money price = new Money("1000");
    private Quantity quantity = new Quantity(1);
    private boolean available = true;

    private ShoppingCartItemTestDataBuilder() {
    }

    public static ShoppingCartItemTestDataBuilder aShoppingCartItem() {
        return new ShoppingCartItemTestDataBuilder();
    }

    public ShoppingCartItem build() {
        return ShoppingCartItem.brandNew()
                .shoppingCartId(shoppingCartId)
                .productId(productId)
                .productName(productName)
                .price(price)
                .quantity(quantity)
                .available(available)
                .build();
    }

    public ShoppingCartItemTestDataBuilder shoppingCartId(ShoppingCartId shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
        return this;
    }

    public ShoppingCartItemTestDataBuilder productId(ProductId productId) {
        this.productId = productId;
        return this;
    }

    public ShoppingCartItemTestDataBuilder productName(ProductName productName) {
        this.productName = productName;
        return this;
    }

    public ShoppingCartItemTestDataBuilder price(Money price) {
        this.price = price;
        return this;
    }

    public ShoppingCartItemTestDataBuilder quantity(Quantity quantity) {
        this.quantity = quantity;
        return this;
    }

    public ShoppingCartItemTestDataBuilder available(boolean available) {
        this.available = available;
        return this;
    }
}
