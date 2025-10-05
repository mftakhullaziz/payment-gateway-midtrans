package com.app.midtrans.shared.handler;

import com.app.midtrans.shared.exception.BusinessException;
import com.app.midtrans.shared.payload.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Response<Object>> handleBusinessException(BusinessException ex) {
        Response<Object> response = new Response<>();
        response.setSuccess(false);
        response.setStatusCode(ex.getStatusCode());
        response.setMessage(ex.getMessage());
        response.setData(null);

        return ResponseEntity.status(ex.getStatusCode()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<Object>> handleGenericException(Exception ex) {
        Response<Object> response = new Response<>();
        response.setSuccess(false);
        response.setStatusCode(500);
        response.setMessage("Internal server error: " + ex.getMessage());
        response.setData(null);

        return ResponseEntity.status(500).body(response);
    }
}
