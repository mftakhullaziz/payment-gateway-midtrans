package com.application.paymentmidtransservice.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    @JsonProperty(value = "payment_id")
    private Integer paymentId;

    @JsonProperty(value = "user_id")
    private Integer userId;

    @JsonProperty(value = "order_id")
    private String orderId;

    @JsonProperty(value = "transaction_id")
    private String transactionId;

    @JsonProperty(value = "merchant_id")
    private String merchantId;

    @JsonProperty(value = "gross_amount")
    private Double grossAmount;

    @JsonProperty(value = "currency")
    private String currency;

    @JsonProperty(value = "transaction_time")
    private Timestamp transactionTime;

    @JsonProperty(value = "transaction_status")
    private String transactionStatus;

    @JsonProperty(value = "expiry_time")
    private Timestamp expiryTime;

    @JsonProperty(value = "fraud_status")
    private String fraudStatus;

    @JsonProperty(value = "payment_type")
    private String paymentType;

    @JsonProperty(value = "payment_method")
    private String paymentMethod;

    @JsonProperty(value = "payment_numbers")
    private String paymentVaNumbers;

    @JsonProperty(value = "total_price")
    private Double totalPrice;

    @JsonProperty(value = "total_tax")
    private Double totalTax;

    @JsonProperty(value = "total_discount")
    private Double totalDiscount;
}
