package com.app.gatepay.infra.adapter.inbound.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VaTransferRequest {
    private Long customerId;
    private String orderId;
    private BigDecimal totalAmount;
    private String bankTransfer;
}
