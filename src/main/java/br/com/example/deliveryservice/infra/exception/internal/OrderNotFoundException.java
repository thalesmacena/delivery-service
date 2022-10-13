package br.com.example.deliveryservice.infra.exception.internal;


public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(String message) {
        super(message);
    }
}