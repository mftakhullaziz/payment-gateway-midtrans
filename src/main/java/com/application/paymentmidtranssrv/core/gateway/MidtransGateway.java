package com.application.paymentmidtranssrv.core.gateway;

import com.application.paymentmidtranssrv.domain.model.PaymentMidtrans;
import com.application.paymentmidtranssrv.domain.response.PaymentMidtransResponse;

public interface MidtransGateway {
    PaymentMidtransResponse executePayMidtransBankTransfer(PaymentMidtrans paymentMidtrans);
    PaymentMidtransResponse executePayMidtransQRISAndEWallet(PaymentMidtrans paymentMidtrans);
    PaymentMidtransResponse executePayMidtransCreditCard(PaymentMidtrans paymentMidtrans);
    PaymentMidtransResponse executePayMidtransCSStore(PaymentMidtrans paymentMidtrans);
    PaymentMidtransResponse executePayMidtransCardlessCredit(PaymentMidtrans paymentMidtrans);
}
