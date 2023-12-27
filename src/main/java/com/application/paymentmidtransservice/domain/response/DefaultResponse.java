package com.application.paymentmidtransservice.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DefaultResponse<T> {
    @JsonProperty(value = "message")
    private String message;

    @JsonProperty(value = "status_code")
    private Integer statusCode;

    @JsonProperty(value = "is_success")
    private boolean isSuccess;

    @JsonProperty(value = "data")
    private T data;
}
