package com.app.midtrans.infra.adapter.outbound.cache;

import com.app.midtrans.domain.cache.CachePort;
import com.app.midtrans.shared.exception.RedisCacheException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RedisCacheAdapter implements CachePort {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public <T> void put(String key, T value, Duration ttl) {
        try {
            String json = objectMapper.writeValueAsString(value);
            redisTemplate.opsForValue().set(key, json, ttl);
        } catch (Exception e) {
            throw new RedisCacheException("Failed to cache value " + e.getMessage());
        }
    }

    @Override
    public <T> Optional<T> get(String key, Class<T> clazz) {
        String json = redisTemplate.opsForValue().get(key);
        if (json == null) return Optional.empty();

        try {
            return Optional.of(objectMapper.readValue(json, clazz));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

}

