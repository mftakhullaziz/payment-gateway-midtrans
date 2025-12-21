package com.app.midtrans.infra.adapter.outbound.client.mailtrap;

import com.app.midtrans.domain.email.EmailGatewayPort;

public class EmailGatewayAdapter implements EmailGatewayPort {

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
