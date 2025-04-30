package com.integration.paymentmidtrans.port.inbound.jpagateway;

import com.integration.paymentmidtrans.domain.coreapis.dto.Payment;
import com.integration.paymentmidtrans.domain.coreapis.request.CreatePaymentRequest;

public interface PaymentGateway {
    Payment savePaymentTransaction(CreatePaymentRequest createPaymentRequest);

    Payment findByOrderId(String orderId);

    void updatePayment(String transactionStatus,
                       String orderId,
                       String transactionId);

    Long findCustomerIdByOrderIdAndTransactionId(
        String orderId, String transactionId);
}
