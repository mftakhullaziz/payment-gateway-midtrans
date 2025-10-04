package com.integration.paymentmidtrans.ports.outbound.midtrans;

import com.integration.paymentmidtrans.shared.dto.coreapis.VaTransferDTO;
import com.integration.paymentmidtrans.adapter.inbound.delivery.coreapis.response.PaymentMidtransResponse;

public interface MidtransCoreAPIOutboundPort {
    PaymentMidtransResponse executePayMidtransBankTransfer(VaTransferDTO vaTransferDTO);
    PaymentMidtransResponse executePayMidtransQRISAndEWallet(VaTransferDTO vaTransferDTO);
    PaymentMidtransResponse executePayMidtransCreditCard(VaTransferDTO vaTransferDTO);
    PaymentMidtransResponse executePayMidtransCSStore(VaTransferDTO vaTransferDTO);
    PaymentMidtransResponse executePayMidtransCardlessCredit(VaTransferDTO vaTransferDTO);
}
