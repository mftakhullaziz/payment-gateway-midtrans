package com.app.midtrans.infra.adapter.outbound.client.midtrans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MidtransVARequest {

    @JsonProperty("payment_type")
    private String paymentType;

    @JsonProperty("transaction_details")
    private TransactionDetails transactionDetails;

    // bank_transfer (BCA, BNI, BRI, CIMB)
    @JsonProperty("bank_transfer")
    private BankTransfer bankTransfer;

    // Mandiri
    private EChannel echannel;

    @Data
    public static class TransactionDetails {
        @JsonProperty("order_id")
        private String orderId;

        @JsonProperty("gross_amount")
        private Long grossAmount;
    }

    @Data
    public static class BankTransfer {
        private String bank;
    }

    @Data
    public static class EChannel {
        @JsonProperty("bill_info1")
        private String billInfo1;

        @JsonProperty("bill_info2")
        private String billInfo2;
    }

}

