package com.application.paymentmidtranssrv.core.gateway.transform;

import com.application.paymentmidtranssrv.domain.model.VaTransferMidtrans;
import com.application.paymentmidtranssrv.domain.model.bank.BcaVa;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VaTransferTransformer {

    public static BcaVa transformToBCAVABody(VaTransferMidtrans vaTransferMidtrans) {
        return BcaVa.builder()
            .paymentType(vaTransferMidtrans.getPaymentTypes().toString().toLowerCase())

            .transactionDetails(BcaVa.TransactionDetails.builder()
                .orderId(vaTransferMidtrans.getOrderId())
                .grossAmount(vaTransferMidtrans.getTotalPrice().intValue())
                .build())

            .customerDetails(BcaVa.CustomerDetails.builder()
                .email(vaTransferMidtrans.getCustomerInfo().getEmail())
                .firstName(vaTransferMidtrans.getCustomerInfo().getFirstname())
                .lastName(vaTransferMidtrans.getCustomerInfo().getLastname())
                .phone(vaTransferMidtrans.getCustomerInfo().getPhone())
                .build())

            .itemDetails(vaTransferMidtrans.getOrderItems().stream().map(item -> BcaVa.ItemDetail.builder()
                .id(item.getItemId())
                .name(item.getItemName())
                .price(item.getItemPrice().intValue())
                .quantity(item.getItemQuantity())
                .build()).toList())

            .bankTransfer(BcaVa.BankTransfer.builder()
                .bank(vaTransferMidtrans.getBankType().toString().toLowerCase())
                // .vaNumber(paymentMidtrans.getVaNumber())
                .freeText(BcaVa.FreeText.builder()
                    .inquiry(List.of(BcaVa.TextContent.builder()
                        .id("Silakan lakukan pembayaran sebelum jatuh tempo.")
                        .en("Please complete your payment before the due date.")
                        .build()))
                    .payment(List.of(BcaVa.TextContent.builder()
                        .id("Terima kasih telah melakukan pembayaran.")
                        .en("Thank you for your payment.")
                        .build()))
                    .build())
                .bca(BcaVa.Bca.builder()
                    .subCompanyCode(vaTransferMidtrans.getSubCompanyCode().getCompanyCode())
                    .build())
                .build())

            .build();
    }
}
