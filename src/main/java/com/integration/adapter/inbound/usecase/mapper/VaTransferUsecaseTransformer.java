package com.integration.adapter.inbound.usecase.mapper;

import com.app.midtrans.shared.dto.coreapis.VaTransferDTO;
import com.integration.adapter.inbound.delivery.coreapis.request.PaymentRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VaTransferUsecaseTransformer {

    public static VaTransferDTO transformToVATransferMidtrans(PaymentRequest request) {
        return VaTransferDTO.builder()
            .paymentTypes(request.getPaymentType())
            .bankType(request.getBankType())
            .orderId(request.getOrderId())
            .totalPrice(request.getTotalPrice())
            .totalTax(request.getTotalTax())
            .totalDiscount(request.getTotalDiscount())
            .customerInfo(VaTransferDTO.CustomerInfo.builder()
                .customerId(request.getCustomerInfo().getCustomerId())
                .email(request.getCustomerInfo().getEmail())
                .firstname(request.getCustomerInfo().getFirstname())
                .lastname(request.getCustomerInfo().getLastname())
                .phone(request.getCustomerInfo().getPhone())
                .build())
            .orderItems(request.getOrderItems().stream().map(
                itemRequest -> VaTransferDTO.OrderItems.builder()
                    .itemId(itemRequest.getItemId())
                    .itemName(itemRequest.getItemName())
                    .itemPrice(itemRequest.getItemPrice())
                    .itemDiscount(itemRequest.getItemDiscount())
                    .itemTax(itemRequest.getItemTax())
                    .itemAmount(itemRequest.getItemAmount())
                    .itemQuantity(itemRequest.getItemQty())
                    .build()
            ).toList())
            .subCompanyCode(VaTransferDTO.SubCompanyCode.builder()
                .companyCode(request.getSubCompanyCode().getCompanyCode())
                .build())
            .build();
    }

}
