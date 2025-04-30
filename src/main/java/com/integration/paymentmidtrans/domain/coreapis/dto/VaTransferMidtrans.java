package com.integration.paymentmidtrans.domain.coreapis.dto;

import com.integration.paymentmidtrans.shared.enums.BankType;
import com.integration.paymentmidtrans.shared.enums.PaymentTypes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VaTransferMidtrans {
    private PaymentTypes paymentTypes;
    private BankType bankType;
    private CustomerInfo customerInfo;
    private Double totalPrice;
    private Double totalTax;
    private Double totalDiscount;
    private String orderId;
    private List<OrderItems> orderItems;
    private SubCompanyCode subCompanyCode;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CustomerInfo {
        private Long customerId;
        private String email;
        private String firstname;
        private String lastname;
        private String phone;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderItems {
        private String itemId;
        private String itemName;
        private Double itemPrice;
        private int itemQuantity;
        private Double itemAmount;
        private Double itemTax;
        private Double itemDiscount;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SubCompanyCode {
        private String companyCode;
    }
}
