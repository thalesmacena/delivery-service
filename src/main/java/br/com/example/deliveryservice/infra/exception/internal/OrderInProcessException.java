package br.com.example.deliveryservice.infra.exception.internal;

public class OrderInProcessException extends RuntimeException {

    public OrderInProcessException(String message) {
        super(message);
    }

}
