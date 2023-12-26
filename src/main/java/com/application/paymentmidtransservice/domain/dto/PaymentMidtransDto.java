package com.application.paymentmidtransservice.domain.dto;

import com.application.paymentmidtransservice.domain.BankType;
import com.application.paymentmidtransservice.domain.PaymentTypes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMidtransDto {
    private PaymentTypes paymentTypes;
    private BankType bankType;
    private UsersInfo usersInfo;
    private Double totalPrice;
    private Double totalTax;
    private Double totalDiscount;
    private String orderId;
    private List<ProductItems> productItems;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UsersInfo {
        private String userId;
        private String email;
        private String name;
        private String phone;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductItems {
        private String productName;
        private String productPrice;
        private String productAmount;
        private Double productDiscount;
        private Double productTax;
    }
}
