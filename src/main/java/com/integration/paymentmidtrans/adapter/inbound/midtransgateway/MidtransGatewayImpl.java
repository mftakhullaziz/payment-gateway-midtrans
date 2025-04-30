package com.integration.paymentmidtrans.adapter.inbound.midtransgateway;

import com.integration.paymentmidtrans.domain.coreapis.request.VAChargeRequest;
import com.integration.paymentmidtrans.shared.restclient.RestClientInvoker;
import com.integration.paymentmidtrans.adapter.inbound.mapper.VATransferMapper;
import com.integration.paymentmidtrans.shared.annotation.Gateway;
import com.integration.paymentmidtrans.app.property.PaymentProperty;
import com.integration.paymentmidtrans.port.inbound.midtransgateway.MidtransGateway;
import com.integration.paymentmidtrans.adapter.inbound.transform.MidtransGatewayTransformer;
import com.integration.paymentmidtrans.shared.enums.BankType;
import com.integration.paymentmidtrans.domain.coreapis.dto.VaTransferMidtrans;
import com.integration.paymentmidtrans.domain.coreapis.response.PaymentMidtransResponse;
import com.integration.paymentmidtrans.shared.utility.Base64Utility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
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


    private JSONObject buildRequestVATransfer(VAChargeRequest vaTransferRequest) {
        BankType bankType = BankType.valueOf(vaTransferRequest.getBankTransfer().getBank());
        return switch (bankType) {
            case BCA -> constructBCATransferRequest(vaTransferRequest);
            case PERMATA -> VATransferMapper.createPermataBankTransferRequest();
            case BNI -> VATransferMapper.createBNIBankTransferRequest();
            case BRI -> VATransferMapper.createBRIBankTransferRequest();
            case MANDIRI -> VATransferMapper.createMandiriEChannelTransferRequest();
            case CIMB -> VATransferMapper.createCIMBBankTransferRequest();
        };
    }

    private VAChargeRequest constructBCATransferRequest(VAChargeRequest vaTransferRequest) {
        return VATransferMapper.createBcaBankTransferRequest(
            vaTransferRequest.getTransactionDetails().getOrderId(),
            vaTransferRequest.getTransactionDetails().getGrossAmount(),
            vaTransferRequest.getBankTransfer().getBank(),
            null,
            vaTransferRequest.getBankTransfer().getBca().getSubCompanyCode(),
            vaTransferRequest.getCustomerDetails(),
            vaTransferRequest.getItemDetails());
    }

//    private VAChargeRequest constructBRITransferRequest(VAChargeRequest vaTransferRequest) {
//        return VATransferMapper.createBRIBankTransferRequest(
//
//        );
//    }

    private PaymentMidtransResponse vaTransferExecutor(JSONObject jsonObjectRequest)
        throws JsonProcessingException
    {
        String serverKeyEncode = Base64Utility.encodeToBase64(paymentProperty.getMidtrans().getServerKey());
        PaymentMidtransResponse response = RestClientInvoker.post(
            paymentProperty.getMidtrans().getPaymentUri(),
            jsonObjectRequest,
            null,
            PaymentMidtransResponse.class,
            serverKeyEncode);

        log.info("2Final Response: {}",
            new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(response));

        if (!response.getStatusCode().equals(201)) {
            log.info("2Error Create Transaction :: Status Code: {}", response.getStatusCode());
        }

        return response;
    }
}
