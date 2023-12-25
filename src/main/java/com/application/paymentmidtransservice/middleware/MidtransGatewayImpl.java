package com.application.paymentmidtransservice.middleware;

import com.application.paymentmidtransservice.config.PaymentConfig;
import com.application.paymentmidtransservice.domain.BankType;
import com.application.paymentmidtransservice.domain.PaymentTypes;
import com.application.paymentmidtransservice.domain.dto.PaymentDto;
import com.application.paymentmidtransservice.domain.dto.VirtualAccountBCADto;
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
public class MidtransGatewayImpl implements MidtransGateway {

    private final PaymentConfig paymentConfig;
    private final RestClient restClient = RestClient.create();

    @SneakyThrows
    @Override
    public PaymentMidtransResponse executePaymentMidtrans(PaymentDto paymentDto) {
        PaymentTypes paymentTypes = paymentDto.getPaymentTypes();
        return switch (paymentTypes) {
            case BANK_TRANSFER -> executeInvokeMidtrans(buildRequestBodyBankTransfer(paymentDto));
            case CREDIT_CARD -> executeInvokeMidtrans(buildRequestBodyCreditCard(paymentDto));
            default -> throw new RuntimeException("not found");
        };
    }

    private Object buildRequestBodyCreditCard(PaymentDto paymentDto) {
        return null;
    }

    private PaymentMidtransResponse executeInvokeMidtrans(Object requestBody) throws JsonProcessingException {
        log.info("Request Body: {}", new ObjectMapper().writerWithDefaultPrettyPrinter()
            .writeValueAsString(requestBody));

        String serverKeyEncode = Base64Util.encodeToBase64(paymentConfig.getMidtrans().getServerKey());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + serverKeyEncode);

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

    private Object buildRequestBodyBankTransfer(PaymentDto requestBody) {
        BankType bankType = requestBody.getBankType();
        return switch (bankType) {
            case BCA -> buildRequestBodyVaBca(requestBody);
            case BRI -> buildRequestBodyVaBri(requestBody);
            default -> throw new RuntimeException("payment type not found");
        };
    }

    private Object buildRequestBodyVaBca(PaymentDto paymentDto) {
        return VirtualAccountBCADto.builder()
            .paymentType(paymentDto.getPaymentTypes().toString().toLowerCase())
            .bankTransfer(new VirtualAccountBCADto.BankTransfer(paymentDto.getBankType().toString().toLowerCase()))
            .transactionDetails(new VirtualAccountBCADto.TransactionDetails(
                paymentDto.getOrderId(),
                paymentDto.getTotalPrice().intValue()
            ))
            .build();
    }

    private Object buildRequestBodyVaBri(Object requestBody) {
        return null;
    }

}
