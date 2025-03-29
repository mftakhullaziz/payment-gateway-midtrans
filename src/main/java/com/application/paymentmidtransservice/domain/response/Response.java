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
public class Response<T> {
    @JsonProperty(value = "message")
    private String message;

    @JsonProperty(value = "status_code")
    private Integer statusCode;

    @JsonProperty(value = "is_success")
    private boolean isSuccess;

    @JsonProperty(value = "data")
    private T data;

    public static <T> Response<T> created(T data) {
        Response<T> response = new Response<>();
        response.setData(data);
        response.setSuccess(data != null);
        response.setStatusCode(201);
        response.setMessage(data != null ? "create transaction successfully!" : "created with no content");
        return response;
    }

    public static <T> Response<T> ok(T data) {
        Response<T> response = new Response<>();
        response.setData(data);
        response.setSuccess(true);
        response.setStatusCode(200);
        response.setMessage("request successfully!");
        return response;
    }

    public static <T> Response<T> updated(T data) {
        Response<T> response = new Response<>();
        response.setData(data);
        response.setSuccess(true);
        response.setStatusCode(200);
        response.setMessage("update transaction successfully!");
        return response;
    }

    public static <T> Response<T> noContent() {
        Response<T> response = new Response<>();
        response.setData(null);
        response.setSuccess(true);
        response.setStatusCode(204);
        response.setMessage("no content");
        return response;
    }
}
