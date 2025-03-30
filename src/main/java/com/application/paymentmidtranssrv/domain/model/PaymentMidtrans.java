package com.application.paymentmidtranssrv.domain.model;

import com.application.paymentmidtranssrv.domain.BankType;
import com.application.paymentmidtranssrv.domain.PaymentTypes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMidtrans {
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
