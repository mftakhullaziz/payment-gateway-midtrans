package com.app.midtrans.shared.restclient;

import com.app.midtrans.shared.exception.ExternalApiException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.Map;

@Log4j2
@Component
@RequiredArgsConstructor
public class RestClientInvoker {

    private final ObjectMapper objectMapper;
    private final RestClient restClient;

    public <I, O> O post(
        String uri,
        I request,
        Map<String, String> headers,
        Class<O> responseType
    ) {
        try {
            logRequest(uri, request);
            HttpHeaders httpHeaders = buildHeaders(headers);
            O response = restClient
                .post()
                .uri(uri)
                .headers(h -> h.addAll(httpHeaders))
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(responseType);

            logResponse(response);
            return response;
        } catch (RestClientResponseException ex) {
            log.error(
                "[RestClientInvoker][ERROR] status={} response={}",
                ex.getStatusCode(),
                ex.getResponseBodyAsString()
            );
            throw new ExternalApiException("External API error", ex);
        }
    }

    /* ================= PRIVATE ================= */

    private HttpHeaders buildHeaders(Map<String, String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        if (headers != null) {
            headers.forEach(httpHeaders::add);
        }
        return httpHeaders;
    }

    private void logRequest(String uri, Object body) {
        try {
            log.info("[POST] uri={} body={}",
                uri,
                objectMapper.writeValueAsString(body)
            );
        } catch (Exception ignored) {
            log.info("[POST] uri={} body=[unserializable]", uri);
        }
    }

    private void logResponse(Object response) {
        try {
            log.info("[RESPONSE] {}",
                objectMapper.writeValueAsString(response)
            );
        } catch (Exception ignored) {
            log.info("[RESPONSE] [unserializable]");
        }
    }
}
