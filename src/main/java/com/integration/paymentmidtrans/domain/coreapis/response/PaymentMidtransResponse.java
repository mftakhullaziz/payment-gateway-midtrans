package com.integration.paymentmidtrans.domain.coreapis.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMidtransResponse {
    @JsonProperty(value = "status_code")
    private Integer statusCode;

    @JsonProperty(value = "status_message")
    private String statusMessage;

    @JsonProperty(value = "transaction_id")
    private String transactionId;

    @JsonProperty(value = "order_id")
    private String orderId;

    @JsonProperty(value = "merchant_id")
    private String merchantId;

    @JsonProperty(value = "gross_amount")
    private String grossAmount;

    @JsonProperty(value = "currency")
    private String currency;

    @JsonProperty(value = "payment_type")
    private String paymentType;

    @JsonProperty(value = "transaction_time")
    private String transactionTime;

    @JsonProperty(value = "transaction_status")
    private String transactionStatus;

    @JsonProperty(value = "expiry_time")
    private String expiryTime;

    @JsonProperty(value = "fraud_status")
    private String fraudStatus;

    @JsonProperty(value = "va_numbers")
    private List<VANumbers> vaNumbers;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VANumbers {
        @JsonProperty(value = "bank")
        private String bank;

        @JsonProperty(value = "va_number")
        private String va_number;
    }
}
