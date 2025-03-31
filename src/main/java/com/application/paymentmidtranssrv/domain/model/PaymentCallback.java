package com.application.paymentmidtranssrv.domain.model;

import com.application.paymentmidtranssrv.domain.request.VaTransferCallbackRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCallback {
    private String transactionId;
    private String orderId;
    private VaTransferCallbackRequest callbacks;
}
