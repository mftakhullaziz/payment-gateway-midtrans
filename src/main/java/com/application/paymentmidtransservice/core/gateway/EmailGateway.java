package com.application.paymentmidtransservice.core.gateway;

public interface EmailGateway {
    void publishEmailNotification(String email, String name);
}
