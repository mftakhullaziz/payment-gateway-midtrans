package com.app.midtrans.domain.callback;

import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.JsonNode;

@Component
public class CallbackService {

    public void validate(Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Callback must not be null");
        }

        require(callback.getTransactionId(), "transactionId");
        require(callback.getOrderId(), "orderId");
        require(callback.getDataCallbacks());

        validatePayload(callback.getDataCallbacks());
    }

    private void require(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                "Callback " + field + " must not be null or empty"
            );
        }
    }

    private void require(JsonNode value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException(
                "Callback " + "dataCallbacks" + " must not be null or empty"
            );
        }
    }

    private void validatePayload(JsonNode payload) {
        requireField(payload, "transaction_status");
        requireField(payload, "payment_type");

        // Optional but recommended
        if (payload.has("va_numbers")) {
            validateVaNumbers(payload.get("va_numbers"));
        }
    }

    private void requireField(JsonNode payload, String field) {
        if (!payload.has(field) || payload.get(field).isNull()) {
            throw new IllegalArgumentException(
                "Callback payload missing required field: " + field
            );
        }
    }

    private void validateVaNumbers(JsonNode vaNumbers) {
        if (!vaNumbers.isArray() || vaNumbers.isEmpty()) {
            throw new IllegalArgumentException("va_numbers must be a non-empty array");
        }

        JsonNode first = vaNumbers.get(0);
        requireField(first, "bank");
        requireField(first, "va_number");
    }
}
