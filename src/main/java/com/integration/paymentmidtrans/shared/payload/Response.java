package com.integration.paymentmidtrans.shared.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

    public static <T> ResponseEntity<Response<T>> create(T data) {
        Response<T> response = new Response<>();
        response.setData(data);
        response.setSuccess(data != null);
        response.setStatusCode(201);
        response.setMessage(data != null ? "create transaction successfully!" : "create with no content");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public static <T> ResponseEntity<Response<T>> ok(T data) {
        Response<T> response = new Response<>();
        response.setData(data);
        response.setSuccess(true);
        response.setStatusCode(200);
        response.setMessage("request successfully!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static <T> ResponseEntity<Response<T>> put(T data) {
        Response<T> response = new Response<>();
        response.setData(data);
        response.setSuccess(true);
        response.setStatusCode(202);
        response.setMessage("update transaction successfully!");
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    public static <T> ResponseEntity<Response<T>> noContent() {
        Response<T> response = new Response<>();
        response.setData(null);
        response.setSuccess(true);
        response.setStatusCode(204);
        response.setMessage("no content");
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
