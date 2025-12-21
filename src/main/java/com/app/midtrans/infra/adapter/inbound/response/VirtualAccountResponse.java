package com.app.midtrans.infra.adapter.inbound.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VirtualAccountResponse {

    private String orderId;
    private String transactionId;
    private String bankTransfer;
    private String totalAmount;
    private String currency;
    private String paymentType;
    private String transactionStatus;
    private String transactionTime;
    private VirtualAccount virtualAccount;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VirtualAccount {
        private String bank;
        private String vaNumber;
    }
}
