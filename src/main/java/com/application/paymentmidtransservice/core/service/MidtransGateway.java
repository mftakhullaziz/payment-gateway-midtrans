package com.application.paymentmidtransservice.core.service;

import com.application.paymentmidtransservice.domain.model.PaymentMidtrans;
import com.application.paymentmidtransservice.domain.response.PaymentMidtransResponse;

public interface MidtransGateway {
    PaymentMidtransResponse executePayMidtransBankTransfer(PaymentMidtrans paymentMidtrans);
    PaymentMidtransResponse executePayMidtransQRISAndEWallet(PaymentMidtrans paymentMidtrans);
    PaymentMidtransResponse executePayMidtransCreditCard(PaymentMidtrans paymentMidtrans);
    PaymentMidtransResponse executePayMidtransCSStore(PaymentMidtrans paymentMidtrans);
    PaymentMidtransResponse executePayMidtransCardlessCredit(PaymentMidtrans paymentMidtrans);
}
