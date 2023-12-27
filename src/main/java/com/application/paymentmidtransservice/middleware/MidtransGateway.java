package com.application.paymentmidtransservice.middleware;

import com.application.paymentmidtransservice.domain.dto.PaymentMidtransDto;

public interface MidtransGateway {
    PaymentMidtransResponse executePayMidtransBankTransfer(PaymentMidtransDto paymentMidtransDto);
    PaymentMidtransResponse executePayMidtransQRISAndEWallet(PaymentMidtransDto paymentMidtransDto);
    PaymentMidtransResponse executePayMidtransCreditCard(PaymentMidtransDto paymentMidtransDto);
    PaymentMidtransResponse executePayMidtransCSStore(PaymentMidtransDto paymentMidtransDto);
    PaymentMidtransResponse executePayMidtransCardlessCredit(PaymentMidtransDto paymentMidtransDto);
}
