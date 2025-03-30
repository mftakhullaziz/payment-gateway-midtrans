package com.application.paymentmidtranssrv.core.gateway;

import com.application.paymentmidtranssrv.domain.model.PaymentCallback;

public interface PaymentCallbackGateway {
    void writeCallbackOnDB(PaymentCallback paymentCallback);
}
