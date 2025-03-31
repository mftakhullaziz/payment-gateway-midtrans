package com.application.paymentmidtranssrv.core.gateway;

import com.application.paymentmidtranssrv.domain.model.Payment;
import com.application.paymentmidtranssrv.domain.request.CreatePaymentRequest;

public interface PaymentGateway {
    Payment savePaymentTransaction(CreatePaymentRequest createPaymentRequest);

    Payment findByOrderId(String orderId);

    void updatePayment(String transactionStatus,
                       String orderId,
                       String transactionId);
}
