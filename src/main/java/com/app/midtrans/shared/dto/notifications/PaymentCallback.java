package com.app.midtrans.shared.dto.notifications;

import com.app.midtrans.infra.adapter.inbound.request.VaTransferNotifyRequest;
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
    private VaTransferNotifyRequest callbacks;
}
