package com.integration.paymentmidtrans.core.ports.outbound;

import com.integration.paymentmidtrans.core.dto.PaymentCallback;

public interface PaymentCallbackGateway {
    void writeCallbackOnDB(PaymentCallback paymentCallback);
}
