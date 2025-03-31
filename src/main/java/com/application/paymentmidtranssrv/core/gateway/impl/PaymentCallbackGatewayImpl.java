package com.application.paymentmidtranssrv.core.gateway.impl;

import com.application.paymentmidtranssrv.app.annotation.Gateway;
import com.application.paymentmidtranssrv.core.gateway.PaymentCallbackGateway;
import com.application.paymentmidtranssrv.domain.model.PaymentCallback;
import com.application.paymentmidtranssrv.infra.mysql.entity.PaymentCallbackEntity;
import com.application.paymentmidtranssrv.infra.mysql.repository.PaymentCallbackRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Gateway
@RequiredArgsConstructor
public class PaymentCallbackGatewayImpl implements PaymentCallbackGateway {

    private final PaymentCallbackRepo paymentCallbackRepo;

    @Override
    public void writeCallbackOnDB(PaymentCallback paymentCallback) {
        paymentCallbackRepo.save(
            PaymentCallbackEntity.builder()
            .transactionId(paymentCallback.getTransactionId())
            .orderId(paymentCallback.getOrderId())
            .dataCallbacks(paymentCallback.getCallbacks())
            .build());
    }
}
