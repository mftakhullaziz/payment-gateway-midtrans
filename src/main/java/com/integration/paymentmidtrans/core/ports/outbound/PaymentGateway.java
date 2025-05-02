package com.integration.paymentmidtrans.core.ports.outbound;

import com.integration.paymentmidtrans.core.dto.Payment;
import com.integration.paymentmidtrans.adapter.inbound.delivery.coreapis.request.CreatePaymentRequest;

public interface PaymentGateway {
    Payment savePaymentTransaction(CreatePaymentRequest createPaymentRequest);

    Payment findByOrderId(String orderId);

    void updatePayment(String transactionStatus,
                       String orderId,
                       String transactionId);

    Long findCustomerIdByOrderIdAndTransactionId(
        String orderId, String transactionId);
}
