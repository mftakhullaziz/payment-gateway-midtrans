package com.integration.paymentmidtrans.shared.restclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Log4j2
public class RestClientInvoker {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static  <T> T post(String uri,
                      Object requestBody,
                      Map<String, String> additionalHeaders,
                      Class<T> responseType,
                      String basicAuthToken) throws JsonProcessingException {
        log.info("[RestClient-POST] Request Body: {}",
            OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(requestBody));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Basic " + basicAuthToken);

        if (additionalHeaders != null && !additionalHeaders.isEmpty()) {
            additionalHeaders.forEach(headers::add);
        }

        ResponseEntity<T> responseEntity = RestClient.create()
            .post()
            .uri(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .headers(httpHeaders -> httpHeaders.addAll(headers))
            .body(requestBody)
            .retrieve()
            .toEntity(responseType);

        log.info("[RestClient-POST] Raw Response: {}",
            OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(responseEntity));

        return responseEntity.getBody();
    }
}
