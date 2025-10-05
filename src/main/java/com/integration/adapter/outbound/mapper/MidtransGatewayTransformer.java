package com.integration.adapter.outbound.mapper;

import com.app.midtrans.shared.dto.coreapis.VaTransferDTO;
import com.app.midtrans.shared.dto.coreapis.BcaVa;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MidtransGatewayTransformer {

    public static BcaVa transformToBCAVABody(VaTransferDTO vaTransferDTO) {
        return BcaVa.builder()
            .paymentType(vaTransferDTO.getPaymentTypes().toString().toLowerCase())

            .transactionDetails(BcaVa.TransactionDetails.builder()
                .orderId(vaTransferDTO.getOrderId())
                .grossAmount(vaTransferDTO.getTotalPrice().intValue())
                .build())

            .customerDetails(BcaVa.CustomerDetails.builder()
                .email(vaTransferDTO.getCustomerInfo().getEmail())
                .firstName(vaTransferDTO.getCustomerInfo().getFirstname())
                .lastName(vaTransferDTO.getCustomerInfo().getLastname())
                .phone(vaTransferDTO.getCustomerInfo().getPhone())
                .build())

            .itemDetails(vaTransferDTO.getOrderItems().stream().map(item -> BcaVa.ItemDetail.builder()
                .id(item.getItemId())
                .name(item.getItemName())
                .price(item.getItemPrice().intValue())
                .quantity(item.getItemQuantity())
                .build()).toList())

            .bankTransfer(BcaVa.BankTransfer.builder()
                .bank(vaTransferDTO.getBankType().toString().toLowerCase())
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
                    .subCompanyCode(vaTransferDTO.getSubCompanyCode().getCompanyCode())
                    .build())
                .build())

            .build();
    }
}
