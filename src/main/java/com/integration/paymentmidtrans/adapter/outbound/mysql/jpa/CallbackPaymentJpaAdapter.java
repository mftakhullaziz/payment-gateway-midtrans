package com.integration.paymentmidtrans.adapter.outbound.mysql.jpa;

import com.integration.paymentmidtrans.shared.annotation.Gateway;
import com.integration.paymentmidtrans.ports.outbound.mysql.jpa.PaymentCallbackJPAOutboundPort;
import com.integration.paymentmidtrans.shared.dto.notifications.PaymentCallback;
import com.integration.paymentmidtrans.adapter.outbound.mysql.entity.PaymentCallbackEntity;
import com.integration.paymentmidtrans.ports.outbound.mysql.repository.CallbackPaymentRepositoryPort;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Gateway
@RequiredArgsConstructor
public class CallbackPaymentJpaAdapter implements PaymentCallbackJPAOutboundPort {

    private final CallbackPaymentRepositoryPort callbackPaymentRepositoryPort;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void writeCallbackOnDB(PaymentCallback paymentCallback) {
        // transform to jsonNode
        JsonNode jsonCallbacks = objectMapper.valueToTree(paymentCallback.getCallbacks());

        callbackPaymentRepositoryPort.save(
            PaymentCallbackEntity.builder()
            .transactionId(paymentCallback.getTransactionId())
            .orderId(paymentCallback.getOrderId())
            .dataCallbacks(jsonCallbacks)
            .build());
    }
}
