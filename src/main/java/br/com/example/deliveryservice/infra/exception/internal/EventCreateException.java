package br.com.example.deliveryservice.infra.exception.internal;

public class EventCreateException extends RuntimeException {

    public EventCreateException(String message) {
        super(message);
    }
}
