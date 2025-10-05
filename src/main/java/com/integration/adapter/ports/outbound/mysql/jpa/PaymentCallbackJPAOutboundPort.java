package com.integration.adapter.ports.outbound.mysql.jpa;

import com.app.midtrans.shared.dto.notifications.PaymentCallback;

public interface PaymentCallbackJPAOutboundPort {
    void writeCallbackOnDB(PaymentCallback paymentCallback);
}
