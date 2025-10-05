package com.app.midtrans.infrastructure.adapter.outbound.client;

import com.app.midtrans.domain.email.EmailPort;

public class EmailClient implements EmailPort {

    @Override
    public void publishEmailRemainderNotification(
        String email,
        String name,
        String virtualAccountNumber,
        String bankType,
        String expiredTime,
        String status
    ) {

    }

    @Override
    public void publishEmailStatusNotification(
        String email,
        String name,
        String orderId,
        String virtualAccountNumber,
        String bankType,
        String settlementTime,
        String status,
        String totalAmount
    ) {

    }

}
