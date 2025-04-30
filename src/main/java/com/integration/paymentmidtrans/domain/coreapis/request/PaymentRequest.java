package com.integration.paymentmidtrans.domain.coreapis.request;

import com.integration.paymentmidtrans.shared.enums.BankType;
import com.integration.paymentmidtrans.shared.enums.PaymentTypes;
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
public class PaymentRequest {

    @JsonProperty("transfer_type")
    private PaymentTypes paymentType;

    @JsonProperty("transfer_bank")
    private BankType bankType;

    @JsonProperty("customer")
    private CustomerInfo customerInfo;

    @JsonProperty("total_price")
    private Double totalPrice;

    @JsonProperty("total_tax")
    private Double totalTax;

    @JsonProperty("total_discount")
    private Double totalDiscount;

    @JsonProperty("order_id")
    private String orderId;

    @JsonProperty("order_items")
    private List<OrderItems> orderItems;

    @JsonProperty("sub_company")
    private SubCompanyCode subCompanyCode;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CustomerInfo {
        @JsonProperty("customer_id")
        private Long customerId;

        @JsonProperty("email")
        private String email;

        @JsonProperty("firstname")
        private String firstname;

        @JsonProperty("lastname")
        private String lastname;

        @JsonProperty("phone")
        private String phone;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderItems {
        @JsonProperty("item_id")
        private String itemId;

        @JsonProperty("item_name")
        private String itemName;

        @JsonProperty("item_price")
        private Double itemPrice;

        @JsonProperty("item_amount")
        private Double itemAmount;

        @JsonProperty("item_discount")
        private Double itemDiscount;

        @JsonProperty("item_tax")
        private Double itemTax;

        @JsonProperty("item_qty")
        private int itemQty;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SubCompanyCode {
        @JsonProperty("company_code")
        private String companyCode;
    }
}
