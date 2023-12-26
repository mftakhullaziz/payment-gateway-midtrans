package com.application.paymentmidtransservice.middleware;

import com.application.paymentmidtransservice.domain.dto.PaymentMidtransDto;

public interface MidtransGateway {
    PaymentMidtransResponse executePaymentMidtrans(PaymentMidtransDto paymentMidtransDto);
}
