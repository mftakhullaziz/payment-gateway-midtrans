package com.integration.adapter.ports.outbound.midtrans;

import com.app.midtrans.shared.dto.coreapis.VaTransferDTO;
import com.integration.adapter.inbound.delivery.coreapis.response.PaymentMidtransResponse;

public interface MidtransCoreAPIOutboundPort {
    PaymentMidtransResponse executePayMidtransBankTransfer(VaTransferDTO vaTransferDTO);
    PaymentMidtransResponse executePayMidtransQRISAndEWallet(VaTransferDTO vaTransferDTO);
    PaymentMidtransResponse executePayMidtransCreditCard(VaTransferDTO vaTransferDTO);
    PaymentMidtransResponse executePayMidtransCSStore(VaTransferDTO vaTransferDTO);
    PaymentMidtransResponse executePayMidtransCardlessCredit(VaTransferDTO vaTransferDTO);
}
