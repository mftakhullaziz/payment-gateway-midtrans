package com.application.paymentmidtransservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VirtualAccountBCADto {

    @JsonProperty(value = "payment_type")
    private String paymentType;

    @JsonProperty(value = "transaction_details")
    private TransactionDetails transactionDetails;

    @JsonProperty(value = "bank_transfer")
    private BankTransfer bankTransfer;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TransactionDetails {
        @JsonProperty(value = "order_id")
        private String orderId;

        @JsonProperty(value = "gross_amount")
        private Integer grossAmount;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BankTransfer {
        @JsonProperty(value = "bank")
        private String bank;
    }
}
