package com.app.midtrans.domain.cache;

import java.time.Duration;
import java.util.Optional;

public interface CachePort {

    <T> void put(String key, T value, Duration ttl);

    <T> Optional<T> get(String key, Class<T> clazz);

    void delete(String key);
}
