package br.com.example.deliveryservice.infra.exception.internal;

public class ProductKeyAlreadyInUse extends RuntimeException {

    public ProductKeyAlreadyInUse(String message) {
        super(message);
    }
}
