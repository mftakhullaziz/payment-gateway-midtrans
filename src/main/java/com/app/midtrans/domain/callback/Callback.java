package com.app.midtrans.domain.callback;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Callback {
    private String transactionId;
    private String orderId;
    private JsonNode dataCallbacks;
}
