package br.com.example.deliveryservice.domain.services;

public interface CacheLockService {

    void lock(String key, Object object);

    void unlock(String key);

    boolean isLocked(String key);

}
