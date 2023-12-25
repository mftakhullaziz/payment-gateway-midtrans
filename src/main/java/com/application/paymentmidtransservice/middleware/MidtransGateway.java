package com.application.paymentmidtransservice.middleware;

import com.application.paymentmidtransservice.domain.dto.PaymentDto;

public interface MidtransGateway {
    PaymentMidtransResponse executePaymentMidtrans(PaymentDto paymentDto);
}
