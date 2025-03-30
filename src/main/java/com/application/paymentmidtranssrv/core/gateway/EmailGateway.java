package com.application.paymentmidtranssrv.core.gateway;

public interface EmailGateway {
    void publishEmailRemainderNotification(String email,
                                           String name,
                                           String virtualAccountNumber,
                                           String bankType,
                                           String expiredTime,
                                           String status);

    void publishEmailStatusNotification();
}
