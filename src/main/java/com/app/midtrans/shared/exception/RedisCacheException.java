package com.app.midtrans.shared.exception;

public class RedisCacheException extends RuntimeException {
    public RedisCacheException(String message) {
        super(message);
    }
}
