package com.app.gatepay.shared.exception;

public class InvalidCustomerRoleException extends RuntimeException {
    public InvalidCustomerRoleException(String message) {
        super(message);
    }
}
