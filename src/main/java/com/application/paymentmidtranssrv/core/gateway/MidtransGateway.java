package com.application.paymentmidtranssrv.core.gateway;

import com.application.paymentmidtranssrv.domain.model.VaTransferMidtrans;
import com.application.paymentmidtranssrv.domain.response.PaymentMidtransResponse;

public interface MidtransGateway {
    PaymentMidtransResponse executePayMidtransBankTransfer(VaTransferMidtrans vaTransferMidtrans);
    PaymentMidtransResponse executePayMidtransQRISAndEWallet(VaTransferMidtrans vaTransferMidtrans);
    PaymentMidtransResponse executePayMidtransCreditCard(VaTransferMidtrans vaTransferMidtrans);
    PaymentMidtransResponse executePayMidtransCSStore(VaTransferMidtrans vaTransferMidtrans);
    PaymentMidtransResponse executePayMidtransCardlessCredit(VaTransferMidtrans vaTransferMidtrans);
}
