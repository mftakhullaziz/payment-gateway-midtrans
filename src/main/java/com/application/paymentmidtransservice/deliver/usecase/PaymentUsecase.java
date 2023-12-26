package com.application.paymentmidtransservice.deliver.usecase;

import com.application.paymentmidtransservice.domain.request.PaymentRequest;
import com.application.paymentmidtransservice.domain.response.PaymentResponse;

public interface PaymentUsecase {
    PaymentResponse executePaymentTransaction(PaymentRequest paymentRequest);
}
