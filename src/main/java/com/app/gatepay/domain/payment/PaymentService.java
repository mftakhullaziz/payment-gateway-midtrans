package com.app.gatepay.domain.payment;

import org.springframework.stereotype.Component;

@Component
public class PaymentService {

    public void validateForSave(Payment payment) {
        if (payment == null) {
            throw new IllegalArgumentException("Payment must not be null");
        }

        require(payment.getCustomerId(), "customerId");
        require(payment.getOrderId(), "orderId");
        require(payment.getTransactionId(), "transactionId");
        require(payment.getTotalAmount(), "totalAmount");
        require(payment.getCurrency(), "currency");
        require(payment.getTransactionStatus(), "transactionStatus");
        require(payment.getPaymentType(), "paymentType");
        require(payment.getVaChannel(), "vaChannel");
        require(payment.getTransactionTime(), "transactionTime");

        // VA / Bill validation
        if (payment.isMandiri()) {
            require(payment.getBillKey(), "billKey");
        } else {
            require(payment.getVaNumber(), "vaNumber");
        }
    }

    private void require(Object value, String field) {
        if (value == null) {
            throw new IllegalStateException("Payment " + field + " must not be null");
        }

        if (value instanceof String str && str.isBlank()) {
            throw new IllegalStateException("Payment " + field + " must not be empty");
        }
    }
}
