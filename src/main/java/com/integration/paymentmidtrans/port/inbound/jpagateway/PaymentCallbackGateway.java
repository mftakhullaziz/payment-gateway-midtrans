package com.integration.paymentmidtrans.port.inbound.jpagateway;

import com.integration.paymentmidtrans.domain.notifications.dto.PaymentCallback;

public interface PaymentCallbackGateway {
    void writeCallbackOnDB(PaymentCallback paymentCallback);
}
