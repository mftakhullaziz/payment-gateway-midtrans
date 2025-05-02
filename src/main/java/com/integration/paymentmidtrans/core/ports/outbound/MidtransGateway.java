package com.integration.paymentmidtrans.core.ports.outbound;

import com.integration.paymentmidtrans.core.dto.VaTransferMidtrans;
import com.integration.paymentmidtrans.adapter.inbound.delivery.coreapis.response.PaymentMidtransResponse;

public interface MidtransGateway {
    PaymentMidtransResponse executePayMidtransBankTransfer(VaTransferMidtrans vaTransferMidtrans);
    PaymentMidtransResponse executePayMidtransQRISAndEWallet(VaTransferMidtrans vaTransferMidtrans);
    PaymentMidtransResponse executePayMidtransCreditCard(VaTransferMidtrans vaTransferMidtrans);
    PaymentMidtransResponse executePayMidtransCSStore(VaTransferMidtrans vaTransferMidtrans);
    PaymentMidtransResponse executePayMidtransCardlessCredit(VaTransferMidtrans vaTransferMidtrans);
}
