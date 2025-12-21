package com.app.midtrans.domain.email;

public interface EmailGatewayPort {

    void sendReminderEmail(
        String email,
        String name,
        String virtualAccountNumber,
        String bankType,
        String expiredTime,
        String status
    );

    void sendStatusEmail(
        String email,
        String name,
        String orderId,
        String virtualAccountNumber,
        String bankType,
        String settlementTime,
        String status,
        String totalAmount
    );

}
