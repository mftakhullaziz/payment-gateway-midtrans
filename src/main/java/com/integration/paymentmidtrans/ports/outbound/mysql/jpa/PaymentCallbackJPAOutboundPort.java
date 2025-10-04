package com.integration.paymentmidtrans.ports.outbound.mysql.jpa;

import com.integration.paymentmidtrans.shared.dto.notifications.PaymentCallback;

public interface PaymentCallbackJPAOutboundPort {
    void writeCallbackOnDB(PaymentCallback paymentCallback);
}
