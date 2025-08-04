package com.algaworks.algashop.ordering.domain.valueobject;

import lombok.Builder;

import java.util.Objects;

@Builder
public record ShippingInfo(FullName fullName, Document document, Phone phone, Address address) {
    public ShippingInfo {
        Objects.requireNonNull(fullName);
        Objects.requireNonNull(document);
        Objects.requireNonNull(phone);
        Objects.requireNonNull(address);
    }
}
