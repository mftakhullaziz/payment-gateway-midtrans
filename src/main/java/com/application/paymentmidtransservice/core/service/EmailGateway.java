package com.application.paymentmidtransservice.core.service;

public interface EmailGateway {
    void publishEmailNotification(String email, String name);
}
