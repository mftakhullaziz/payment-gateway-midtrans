package com.app.midtrans.shared.exception;

public class InactiveCustomerException extends RuntimeException {
    public InactiveCustomerException(String message) {
        super(message);
    }
}
