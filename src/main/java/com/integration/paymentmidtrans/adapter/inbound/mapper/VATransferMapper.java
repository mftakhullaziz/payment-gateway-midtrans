package com.integration.paymentmidtrans.adapter.inbound.mapper;

import com.integration.paymentmidtrans.domain.coreapis.request.VAChargeRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VATransferMapper {

    private static final String BANK_TRANSFER = "bank_transfer";

    public static VAChargeRequest createPermataBankTransferRequest(
        String orderId,
        Long grossAmount,
        String recipientName
    ) {
        return VAChargeRequest.builder()
            .paymentType(BANK_TRANSFER)
            .transactionDetails(VAChargeRequest.TransactionDetails.builder()
                .orderId(orderId)
                .grossAmount(grossAmount)
                .build())
            .bankTransfer(VAChargeRequest.BankTransfer.builder()
                .bank("permata")
                .permata(VAChargeRequest.BankTransfer.PermataBank.builder()
                    .recipientName(recipientName)
                    .build())
                .build())
            .build();
    }

    public static VAChargeRequest createBcaBankTransferRequest(
        String orderId,
        Long grossAmount,
        String bank,
        String vaNumber,
        String subCompanyCode,
        VAChargeRequest.CustomerDetails customerDetails,
        List<VAChargeRequest.ItemDetails> itemDetails
    ) {
        return VAChargeRequest.builder()
            .paymentType(BANK_TRANSFER)
            .transactionDetails(VAChargeRequest.TransactionDetails.builder()
                .orderId(orderId)
                .grossAmount(grossAmount)
                .build())
            .customerDetails(customerDetails)
            .itemDetails(itemDetails)
            .bankTransfer(VAChargeRequest.BankTransfer.builder()
                .bank(bank)
                .vaNumber(vaNumber)
                .bca(VAChargeRequest.BankTransfer.BcaBank.builder()
                    .subCompanyCode(subCompanyCode)
                    .build())
                .freeText(Map.of(
                    "inquiry", List.of(
                    VAChargeRequest.BankTransfer.FreeText.builder()
                            .id("Silakan lakukan pembayaran sebelum jatuh tempo.")
                            .en("Please complete your payment before the due date.")
                            .build()
                    ),
                    "payment", List.of(
                    VAChargeRequest.BankTransfer.FreeText.builder()
                            .id("Terimakasih telah melakukan pembayaran.")
                            .en("Thank you for your payment.")
                            .build()
                    )))
                .build())
            .build();
    }

    public static VAChargeRequest createMandiriEChannelTransferRequest(
        String orderId,
        Long grossAmount,
        List<VAChargeRequest.ItemDetails> itemDetails,
        String billInfo1,
        String billInfo2,
        String billKey
    ) {
        return VAChargeRequest.builder()
            .paymentType("echannel")
            .transactionDetails(VAChargeRequest.TransactionDetails.builder()
                .orderId(orderId)
                .grossAmount(grossAmount)
                .build())
            .itemDetails(itemDetails)
            .echannel(VAChargeRequest.EChannel.builder()
                .billInfo1(billInfo1)
                .billInfo2(billInfo2)
                .billKey(billKey)
                .build())
            .build();
    }

    public static VAChargeRequest createBNIBankTransferRequest(
        String orderId,
        Long grossAmount,
        String bank,
        String vaNumber,
        VAChargeRequest.CustomerDetails customerDetails,
        List<VAChargeRequest.ItemDetails> itemDetails
    ) {
        return VAChargeRequest.builder()
            .paymentType(BANK_TRANSFER)
            .transactionDetails(VAChargeRequest.TransactionDetails.builder()
                .orderId(orderId)
                .grossAmount(grossAmount)
                .build())
            .customerDetails(customerDetails)
            .itemDetails(itemDetails)
            .bankTransfer(VAChargeRequest.BankTransfer.builder()
                .bank(bank)
                .vaNumber(vaNumber)
                .build())
            .build();
    }

    public static VAChargeRequest createBRIBankTransferRequest(
        String orderId,
        Long grossAmount,
        String bank,
        String vaNumber,
        VAChargeRequest.CustomerDetails customerDetails,
        List<VAChargeRequest.ItemDetails> itemDetails
    ) {
        return VAChargeRequest.builder()
            .paymentType(BANK_TRANSFER)
            .transactionDetails(VAChargeRequest.TransactionDetails.builder()
                .orderId(orderId)
                .grossAmount(grossAmount)
                .build())
            .customerDetails(customerDetails)
            .itemDetails(itemDetails)
            .bankTransfer(VAChargeRequest.BankTransfer.builder()
                .bank(bank)
                .vaNumber(vaNumber)
                .build())
            .build();
    }

    public static VAChargeRequest createCIMBBankTransferRequest(
        String orderId,
        Long grossAmount,
        String bank,
        String vaNumber,
        VAChargeRequest.CustomerDetails customerDetails,
        List<VAChargeRequest.ItemDetails> itemDetails
    ) {
        return VAChargeRequest.builder()
            .paymentType(BANK_TRANSFER)
            .transactionDetails(VAChargeRequest.TransactionDetails.builder()
                .orderId(orderId)
                .grossAmount(grossAmount)
                .build())
            .customerDetails(customerDetails)
            .itemDetails(itemDetails)
            .bankTransfer(VAChargeRequest.BankTransfer.builder()
                .bank(bank)
                .vaNumber(vaNumber)
                .build())
            .build();
    }
}
