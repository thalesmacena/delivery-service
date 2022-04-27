package br.com.example.deliveryservice.infra.exception.internal;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String message) {
        super(message);
    }
}
