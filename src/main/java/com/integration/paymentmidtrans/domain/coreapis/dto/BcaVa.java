package com.integration.paymentmidtrans.domain.coreapis.dto;

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
public class BcaVa {

    @JsonProperty("payment_type")
    private String paymentType;

    @JsonProperty("transaction_details")
    private TransactionDetails transactionDetails;

    @JsonProperty("customer_details")
    private CustomerDetails customerDetails;

    @JsonProperty("item_details")
    private List<ItemDetail> itemDetails;

    @JsonProperty("bank_transfer")
    private BankTransfer bankTransfer;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TransactionDetails {
        @JsonProperty("order_id")
        private String orderId;

        @JsonProperty("gross_amount")
        private Integer grossAmount;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CustomerDetails {
        private String email;

        @JsonProperty("first_name")
        private String firstName;

        @JsonProperty("last_name")
        private String lastName;

        private String phone;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItemDetail {
        private String id;
        private Integer price;
        private Integer quantity;
        private String name;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BankTransfer {
        private String bank;

//        @JsonProperty("va_number")
//        private String vaNumber;

        @JsonProperty("free_text")
        private FreeText freeText;

        private Bca bca;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FreeText {
        private List<TextContent> inquiry;
        private List<TextContent> payment;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TextContent {
        private String id;
        private String en;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Bca {
        @JsonProperty("sub_company_code")
        private String subCompanyCode;
    }
}
