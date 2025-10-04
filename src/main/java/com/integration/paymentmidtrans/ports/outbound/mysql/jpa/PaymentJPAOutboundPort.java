package com.integration.paymentmidtrans.ports.outbound.mysql.jpa;

import com.integration.paymentmidtrans.shared.dto.coreapis.Payment;
import com.integration.paymentmidtrans.adapter.inbound.delivery.coreapis.request.CreatePaymentRequest;

public interface PaymentJPAOutboundPort {
    Payment savePaymentTransaction(CreatePaymentRequest createPaymentRequest);

    Payment findByOrderId(String orderId);

    void updatePayment(String transactionStatus,
                       String orderId,
                       String transactionId);

    Long findCustomerIdByOrderIdAndTransactionId(
        String orderId, String transactionId);
}
