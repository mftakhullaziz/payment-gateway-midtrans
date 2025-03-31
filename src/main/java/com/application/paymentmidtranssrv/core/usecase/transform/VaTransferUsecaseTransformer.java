package com.application.paymentmidtranssrv.core.usecase.transform;

import com.application.paymentmidtranssrv.domain.model.VaTransferMidtrans;
import com.application.paymentmidtranssrv.domain.request.PaymentRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VaTransferUsecaseTransformer {

    public static VaTransferMidtrans transformToVATransferMidtrans(PaymentRequest request) {
        return VaTransferMidtrans.builder()
            .paymentTypes(request.getPaymentType())
            .bankType(request.getBankType())
            .orderId(request.getOrderId())
            .totalPrice(request.getTotalPrice())
            .totalTax(request.getTotalTax())
            .totalDiscount(request.getTotalDiscount())
            .customerInfo(VaTransferMidtrans.CustomerInfo.builder()
                .customerId(request.getCustomerInfo().getCustomerId())
                .email(request.getCustomerInfo().getEmail())
                .firstname(request.getCustomerInfo().getFirstname())
                .lastname(request.getCustomerInfo().getLastname())
                .phone(request.getCustomerInfo().getPhone())
                .build())
            .orderItems(request.getOrderItems().stream().map(
                itemRequest -> VaTransferMidtrans.OrderItems.builder()
                    .itemId(itemRequest.getItemId())
                    .itemName(itemRequest.getItemName())
                    .itemPrice(itemRequest.getItemPrice())
                    .itemDiscount(itemRequest.getItemDiscount())
                    .itemTax(itemRequest.getItemTax())
                    .itemAmount(itemRequest.getItemAmount())
                    .itemQuantity(itemRequest.getItemQty())
                    .build()
            ).toList())
            .subCompanyCode(VaTransferMidtrans.SubCompanyCode.builder()
                .companyCode(request.getSubCompanyCode().getCompanyCode())
                .build())
            .build();
    }

}
