package com.algaworks.algashop.ordering.domain.model.exception;

public class DomainException extends RuntimeException {

    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }

    public DomainException(String message) {
        super(message);
    }
}
