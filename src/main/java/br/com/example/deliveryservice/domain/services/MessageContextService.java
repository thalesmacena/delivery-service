package br.com.example.deliveryservice.domain.services;

public interface MessageContextService {
    String getMessage(String key);
    String getMessage(String key, Object... args);
}
