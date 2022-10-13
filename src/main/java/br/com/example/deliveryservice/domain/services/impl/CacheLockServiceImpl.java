package br.com.example.deliveryservice.domain.services.impl;

import br.com.example.deliveryservice.domain.services.CacheLockService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CacheLockServiceImpl implements CacheLockService {

    @Value("${application.redis.lock-key}")
    private String lockKey;

    private final RedisTemplate<String, Object> redisTemplate;

    private String formatKey(String key) {
        return this.lockKey + key;
    }

    @Override
    public void lock(String key, Object object) {
        this.redisTemplate.opsForSet().add(this.formatKey(key), object);
    }

    @Override
    public void unlock(String key) {
        this.redisTemplate.delete(this.formatKey(key));
    }

    @Override
    public boolean isLocked(String key) {
        return Boolean.TRUE.equals(this.redisTemplate.hasKey(this.formatKey(key)));
    }
}
