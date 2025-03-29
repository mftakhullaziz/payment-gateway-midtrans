package com.application.paymentmidtransservice.core.gateway;

import com.application.paymentmidtransservice.domain.model.Payment;
import com.application.paymentmidtransservice.domain.request.CreatePaymentRequest;

public interface PaymentGateway {
    Payment savePaymentTransaction(CreatePaymentRequest createPaymentRequest);
}
