package com.application.paymentmidtransservice.core.service;

import com.application.paymentmidtransservice.domain.dto.PaymentDto;
import com.application.paymentmidtransservice.domain.request.CreatePaymentRequest;

public interface PaymentService {
    PaymentDto savePaymentTransaction(CreatePaymentRequest createPaymentRequest);
}
