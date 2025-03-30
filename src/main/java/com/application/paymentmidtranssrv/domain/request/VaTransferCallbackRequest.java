package com.application.paymentmidtranssrv.domain.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VaTransferCallbackRequest {
    // Shared
    @JsonProperty("transaction_time")
    private String transactionTime;

    @JsonProperty("transaction_status")
    private String transactionStatus;

    @JsonProperty("transaction_id")
    private String transactionId;

    @JsonProperty("status_message")
    private String statusMessage;

    @JsonProperty("status_code")
    private String statusCode;

    @JsonProperty("signature_key")
    private String signatureKey;

    @JsonProperty("settlement_time")
    private String settlementTime;

    @JsonProperty("payment_type")
    private String paymentType;

    @JsonProperty("order_id")
    private String orderId;

    @JsonProperty("merchant_id")
    private String merchantId;

    @JsonProperty("gross_amount")
    private String grossAmount;

    @JsonProperty("fraud_status")
    private String fraudStatus;

    private String currency;

    // Common VA fields
    @JsonProperty("va_numbers")
    private List<VirtualAccountNumber> vaNumbers;            // For BCA, BNI, BRI

    @JsonProperty("permata_va_number")
    private String permataVaNumber;             // For Permata

    @JsonProperty("payment_amounts")
    private List<PaymentAmount> paymentAmounts;  // May exist for BNI, etc.

    // Mandiri bill-specific
    @JsonProperty("biller_code")
    private String billerCode;                   // Mandiri only

    @JsonProperty("bill_key")
    private String billKey;                      // Mandiri only

    @Data
    public static class VirtualAccountNumber {
        private String bank;

        @JsonProperty("va_number")
        private String vaNumber;
    }

    @Data
    public static class PaymentAmount {
        @JsonProperty("paid_at")
        private String paidAt;

        private String amount;
    }
}
