package com.integration.paymentmidtrans.adapter.inbound.jpagateway;

import com.integration.paymentmidtrans.shared.annotation.Gateway;
import com.integration.paymentmidtrans.port.inbound.jpagateway.PaymentCallbackGateway;
import com.integration.paymentmidtrans.domain.notifications.dto.PaymentCallback;
import com.integration.paymentmidtrans.adapter.outbound.mysql.entity.PaymentCallbackEntity;
import com.integration.paymentmidtrans.adapter.outbound.mysql.repository.PaymentCallbackRepo;
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
