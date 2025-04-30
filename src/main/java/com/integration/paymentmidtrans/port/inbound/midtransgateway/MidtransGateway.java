package com.integration.paymentmidtrans.port.inbound.midtransgateway;

import com.integration.paymentmidtrans.domain.coreapis.dto.VaTransferMidtrans;
import com.integration.paymentmidtrans.domain.coreapis.response.PaymentMidtransResponse;

public interface MidtransGateway {
    PaymentMidtransResponse executePayMidtransBankTransfer(VaTransferMidtrans vaTransferMidtrans);
    PaymentMidtransResponse executePayMidtransQRISAndEWallet(VaTransferMidtrans vaTransferMidtrans);
    PaymentMidtransResponse executePayMidtransCreditCard(VaTransferMidtrans vaTransferMidtrans);
    PaymentMidtransResponse executePayMidtransCSStore(VaTransferMidtrans vaTransferMidtrans);
    PaymentMidtransResponse executePayMidtransCardlessCredit(VaTransferMidtrans vaTransferMidtrans);
}
