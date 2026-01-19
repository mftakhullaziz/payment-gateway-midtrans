package com.app.gatepay.domain.payment;

import com.app.gatepay.shared.enums.VaChannel;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Payment {
    private Long customerId;
    private String orderId;
    private BigDecimal totalAmount;
    private String bankTransfer;
    private VaChannel vaChannel;

    private String transactionId;
    private String currency;
    private String paymentType;
    private String transactionStatus;
    private String transactionTime;
    private String expiredTime;

    private String vaBank;
    private String vaNumber;

    private boolean isMandiri;
    private String billKey;
    private String billCode;

    private String provider;
}
