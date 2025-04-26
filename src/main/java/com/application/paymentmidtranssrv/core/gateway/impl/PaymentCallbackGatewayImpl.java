package com.application.paymentmidtranssrv.core.gateway.impl;

import com.application.paymentmidtranssrv.facade.annotation.Gateway;
import com.application.paymentmidtranssrv.core.gateway.PaymentCallbackGateway;
import com.application.paymentmidtranssrv.domain.model.PaymentCallback;
import com.application.paymentmidtranssrv.infrastructure.mysql.entity.PaymentCallbackEntity;
import com.application.paymentmidtranssrv.infrastructure.mysql.repository.PaymentCallbackRepo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Gateway
@RequiredArgsConstructor
public class PaymentCallbackGatewayImpl implements PaymentCallbackGateway {

    private final PaymentCallbackRepo paymentCallbackRepo;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void writeCallbackOnDB(PaymentCallback paymentCallback) {
        // transform to jsonNode
        JsonNode jsonCallbacks = objectMapper.valueToTree(paymentCallback.getCallbacks());

        paymentCallbackRepo.save(
            PaymentCallbackEntity.builder()
            .transactionId(paymentCallback.getTransactionId())
            .orderId(paymentCallback.getOrderId())
            .dataCallbacks(jsonCallbacks)
            .build());
    }
}
