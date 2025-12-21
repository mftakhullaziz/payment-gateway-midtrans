package com.app.midtrans.infra.adapter.inbound.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VaTransferResponse {
    private Long customerId;
    private String email;
    private String orderId;
    private String transactionId;
    private String status;
    private String paymentType;

    private String bank;
    private String virtualAccountNumber;

    private String expiredTime;
    private String transactionTime;
}
