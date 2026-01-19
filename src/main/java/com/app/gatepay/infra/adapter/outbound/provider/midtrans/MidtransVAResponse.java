package com.app.gatepay.infra.adapter.outbound.provider.midtrans;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MidtransVAResponse {

    @JsonProperty("status_code")
    private String statusCode;

    @JsonProperty("status_message")
    private String statusMessage;

    @JsonProperty("transaction_id")
    private String transactionId;

    @JsonProperty("order_id")
    private String orderId;

    @JsonProperty("merchant_id")
    private String merchantId;

    @JsonProperty("gross_amount")
    private String grossAmount;

    private String currency;

    @JsonProperty("payment_type")
    private String paymentType;

    @JsonProperty("transaction_time")
    private String transactionTime;

    @JsonProperty("transaction_status")
    private String transactionStatus;

    @JsonProperty("fraud_status")
    private String fraudStatus;

    // BCA / BNI / BRI / CIMB
    @JsonProperty("va_numbers")
    private List<VaNumber> vaNumbers;

    // Mandiri Bill
    @JsonProperty("bill_key")
    private String billKey;

    @JsonProperty("biller_code")
    private String billerCode;

    // Permata
    @JsonProperty("permata_va_number")
    private String permataVaNumber;

    // Optional expiry
    @JsonProperty("expiry_time")
    private String expiryTime;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VaNumber {
        private String bank;

        @JsonProperty("va_number")
        private String vaNumber;
    }

}

