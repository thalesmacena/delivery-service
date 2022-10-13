package br.com.example.deliveryservice.infra.exception.internal;

public class ProductKeyAlreadyInUseException extends RuntimeException {

    public ProductKeyAlreadyInUseException(String message) {
        super(message);
    }
}
