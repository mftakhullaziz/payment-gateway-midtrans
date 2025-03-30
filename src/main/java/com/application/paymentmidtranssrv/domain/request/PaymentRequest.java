package com.application.paymentmidtranssrv.domain.request;

import com.application.paymentmidtranssrv.domain.BankType;
import com.application.paymentmidtranssrv.domain.PaymentTypes;
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

    @JsonProperty(value = "payment_type")
    private PaymentTypes paymentType;

    @JsonProperty(value = "transfer_via")
    private BankType bankType;

    @JsonProperty(value = "users_info")
    private UsersInfo usersInfo;

    @JsonProperty(value = "total_price")
    private Double totalPrice;

    @JsonProperty(value = "total_tax")
    private Double totalTax;

    @JsonProperty(value = "total_discount")
    private Double totalDiscount;

    @JsonProperty(value = "order_id")
    private String orderId;

    @JsonProperty(value = "product_items")
    private List<ProductItems> productItems;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UsersInfo {
        @JsonProperty(value = "user_id")
        private Integer userId;

        @JsonProperty(value = "email")
        private String email;

        @JsonProperty(value = "name")
        private String name;

        @JsonProperty(value = "phone")
        private String phone;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductItems {
        @JsonProperty(value = "product_name")
        private String productName;

        @JsonProperty(value = "product_price")
        private String productPrice;

        @JsonProperty(value = "product_amount")
        private String productAmount;

        @JsonProperty(value = "product_discount")
        private Double productDiscount;

        @JsonProperty(value = "product_tax")
        private Double productTax;
    }

}
