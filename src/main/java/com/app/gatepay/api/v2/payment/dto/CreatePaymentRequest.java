package com.app.gatepay.api.v2.payment.dto;

import com.app.gatepay.shared.enums.PaymentProvider;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreatePaymentRequest {
    /** Optional. If null, will use configured default provider. */
    private PaymentProvider provider;

    private Long customerId;
    private String orderId;
    private BigDecimal totalAmount;

    /** Payment method discriminator. Phase 1 supports VA and EWALLET. */
    private PaymentMethodType methodType;

    /**
     * Method channel.
     * - VA: bank code/name, e.g. "bca", "bni", "mandiri".
     * - EWALLET: "gopay", "shopeepay", "qris".
     */
    private String channel;
}

