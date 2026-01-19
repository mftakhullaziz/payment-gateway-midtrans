package com.app.gatepay.shared.exception;

public class RedisCacheException extends RuntimeException {
    public RedisCacheException(String message) {
        super(message);
    }
}
