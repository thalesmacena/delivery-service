package br.com.example.deliveryservice.infra.exception.internal;

public class OrderKeyAlreadyInUseException extends RuntimeException {

    public OrderKeyAlreadyInUseException(String message) {
        super(message);
    }
}