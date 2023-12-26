package com.application.paymentmidtransservice.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentRequest {
    private String orderId;
    private String transactionId;
    private String merchantId;
    private Double grossAmount;
    private String currency;
    private Timestamp transactionTime;
    private String transactionStatus;
    private Timestamp expiryTime;
    private String fraudStatus;
    private String paymentType;
    private String paymentMethod;
    private String paymentVaNumbers;
    private Double totalPrice;
    private Double totalTax;
    private Double totalDiscount;
}
