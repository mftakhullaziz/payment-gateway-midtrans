package com.application.paymentmidtranssrv.core.gateway.impl;

import com.application.paymentmidtranssrv.app.annotation.Gateway;
import com.application.paymentmidtranssrv.app.property.PaymentProperty;
import com.application.paymentmidtranssrv.core.gateway.MidtransGateway;
import com.application.paymentmidtranssrv.core.gateway.transform.MidtransGatewayTransformer;
import com.application.paymentmidtranssrv.domain.BankType;
import com.application.paymentmidtranssrv.domain.model.VaTransferMidtrans;
import com.application.paymentmidtranssrv.domain.response.PaymentMidtransResponse;
import com.application.paymentmidtranssrv.utility.Base64Utility;
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
@Gateway
@RequiredArgsConstructor
public class MidtransGatewayImpl implements MidtransGateway {

    private final PaymentProperty paymentProperty;

    @SneakyThrows
    @Override
    public PaymentMidtransResponse executePayMidtransBankTransfer(VaTransferMidtrans vaTransferMidtrans) {
//        PaymentTypes paymentTypes = paymentMidtransDto.getPaymentTypes();
//        return switch (paymentTypes) {
//            case BANK_TRANSFER -> executeInvokeMidtrans(buildRequestBodyBankTransfer(paymentMidtransDto));
//            case CREDIT_CARD -> executeInvokeMidtrans(buildRequestBodyCreditCard(paymentMidtransDto));
//            default -> throw new RuntimeException("not found");
//        };
        return executeInvokeMidtrans(buildRequestBodyBankTransfer(vaTransferMidtrans));
    }

    @Override
    public PaymentMidtransResponse executePayMidtransQRISAndEWallet(VaTransferMidtrans vaTransferMidtrans) {
        return null;
    }

    @Override
    public PaymentMidtransResponse executePayMidtransCreditCard(VaTransferMidtrans vaTransferMidtrans) {
        return null;
    }

    @Override
    public PaymentMidtransResponse executePayMidtransCSStore(VaTransferMidtrans vaTransferMidtrans) {
        return null;
    }

    @Override
    public PaymentMidtransResponse executePayMidtransCardlessCredit(VaTransferMidtrans vaTransferMidtrans) {
        return null;
    }

    private Object buildRequestBodyCreditCard(VaTransferMidtrans vaTransferMidtrans) {
        return null;
    }

    private PaymentMidtransResponse executeInvokeMidtrans(Object requestBody) throws JsonProcessingException {
        log.info("Request Body: {}", new ObjectMapper().writerWithDefaultPrettyPrinter()
            .writeValueAsString(requestBody));

        String serverKeyEncode = Base64Utility.encodeToBase64(paymentProperty.getMidtrans().getServerKey());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + serverKeyEncode);

        RestClient restClient = RestClient.create();
        ResponseEntity<Object> responseEntity = restClient.post()
            .uri(paymentProperty.getMidtrans().getPaymentUri())
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

    private Object buildRequestBodyBankTransfer(VaTransferMidtrans requestBody) {
        BankType bankType = requestBody.getBankType();
        return switch (bankType) {
            case BCA -> MidtransGatewayTransformer.transformToBCAVABody(requestBody);
            case BRI -> buildRequestBodyVaBri(requestBody);
            default -> throw new RuntimeException("payment type not found");
        };
    }

    private Object buildRequestBodyVaBri(Object requestBody) {
        return null;
    }

}
