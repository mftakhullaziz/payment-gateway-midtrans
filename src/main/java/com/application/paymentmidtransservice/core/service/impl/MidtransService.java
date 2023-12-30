package com.application.paymentmidtransservice.core.service.impl;

import com.application.paymentmidtransservice.config.PaymentConfig;
import com.application.paymentmidtransservice.core.service.MidtransGateway;
import com.application.paymentmidtransservice.domain.BankType;
import com.application.paymentmidtransservice.domain.model.PaymentMidtrans;
import com.application.paymentmidtransservice.domain.model.bank.BcaVa;
import com.application.paymentmidtransservice.domain.response.PaymentMidtransResponse;
import com.application.paymentmidtransservice.util.Base64Util;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

@Log4j2
@RequiredArgsConstructor
public class MidtransService implements MidtransGateway {

    private final PaymentConfig paymentConfig;

    @SneakyThrows
    @Override
    public PaymentMidtransResponse executePayMidtransBankTransfer(PaymentMidtrans paymentMidtrans) {
//        PaymentTypes paymentTypes = paymentMidtransDto.getPaymentTypes();
//        return switch (paymentTypes) {
//            case BANK_TRANSFER -> executeInvokeMidtrans(buildRequestBodyBankTransfer(paymentMidtransDto));
//            case CREDIT_CARD -> executeInvokeMidtrans(buildRequestBodyCreditCard(paymentMidtransDto));
//            default -> throw new RuntimeException("not found");
//        };
        return executeInvokeMidtrans(buildRequestBodyBankTransfer(paymentMidtrans));
    }

    @Override
    public PaymentMidtransResponse executePayMidtransQRISAndEWallet(PaymentMidtrans paymentMidtrans) {
        return null;
    }

    @Override
    public PaymentMidtransResponse executePayMidtransCreditCard(PaymentMidtrans paymentMidtrans) {
        return null;
    }

    @Override
    public PaymentMidtransResponse executePayMidtransCSStore(PaymentMidtrans paymentMidtrans) {
        return null;
    }

    @Override
    public PaymentMidtransResponse executePayMidtransCardlessCredit(PaymentMidtrans paymentMidtrans) {
        return null;
    }

    private Object buildRequestBodyCreditCard(PaymentMidtrans paymentMidtrans) {
        return null;
    }

    private PaymentMidtransResponse executeInvokeMidtrans(Object requestBody) throws JsonProcessingException {
        log.info("Request Body: {}", new ObjectMapper().writerWithDefaultPrettyPrinter()
            .writeValueAsString(requestBody));

        String serverKeyEncode = Base64Util.encodeToBase64(paymentConfig.getMidtrans().getServerKey());
        System.out.println("Encode server key: " + serverKeyEncode);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + serverKeyEncode);

        RestClient restClient = RestClient.create();
        ResponseEntity<Object> responseEntity = restClient.post()
            .uri(paymentConfig.getMidtrans().getPaymentUri())
            .contentType(MediaType.APPLICATION_JSON)
            .headers(httpHeaders -> httpHeaders.addAll(headers))
            .body(requestBody)
            .retrieve()
            .toEntity(Object.class);

        log.info("Raw Response: {}", new ObjectMapper().writerWithDefaultPrettyPrinter()
            .writeValueAsString(responseEntity));

        PaymentMidtransResponse finalResponse = constructToPaymentMidtransResponse(responseEntity.getBody());
        log.info("Final Response: {}", new ObjectMapper().writerWithDefaultPrettyPrinter()
            .writeValueAsString(finalResponse));

        if (!finalResponse.getStatusCode().equals(201))
            log.info("Error Create Transaction :: Status Code: {}", finalResponse.getStatusCode());

        return finalResponse;
    }

    private PaymentMidtransResponse constructToPaymentMidtransResponse(Object resBody) throws JsonProcessingException {
        // Use ObjectMapper to work with the JSON response using JsonNode
        ObjectMapper objectMapper = new ObjectMapper();

        // Convert the response body to a JSON string
        String responseBody = objectMapper.writeValueAsString(resBody);
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        // If needed, you can map the JsonNode to your class
        return objectMapper.treeToValue(jsonNode, PaymentMidtransResponse.class);
    }

    private Object buildRequestBodyBankTransfer(PaymentMidtrans requestBody) {
        BankType bankType = requestBody.getBankType();
        return switch (bankType) {
            case BCA -> buildRequestBodyVaBca(requestBody);
            case BRI -> buildRequestBodyVaBri(requestBody);
            default -> throw new RuntimeException("payment type not found");
        };
    }

    private Object buildRequestBodyVaBca(PaymentMidtrans paymentMidtrans) {
        return BcaVa.builder()
            .paymentType(paymentMidtrans.getPaymentTypes().toString().toLowerCase())
            .bankTransfer(new BcaVa.BankTransfer(paymentMidtrans.getBankType().toString().toLowerCase()))
            .transactionDetails(new BcaVa.TransactionDetails(
                paymentMidtrans.getOrderId(),
                paymentMidtrans.getTotalPrice().intValue()
            ))
            .build();
    }

    private Object buildRequestBodyVaBri(Object requestBody) {
        return null;
    }

}
