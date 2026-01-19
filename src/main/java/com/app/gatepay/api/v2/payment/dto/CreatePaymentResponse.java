package com.app.gatepay.api.v2.payment.dto;

import lombok.Data;

@Data
public class CreatePaymentResponse {
    private Long customerId;
    private String orderId;

    private String provider;
    private String transactionId;

    private String status;
    private String paymentType;

    private String bank;
    private String virtualAccountNumber;

    private String transactionTime;
    private String expiredTime;
}

