package com.app.midtrans.infra.adapter.inbound.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EWalletTransferRequest {
    private Long customerId;
    private String paymentType;
    private String orderId;
    private BigDecimal totalAmount;
}
