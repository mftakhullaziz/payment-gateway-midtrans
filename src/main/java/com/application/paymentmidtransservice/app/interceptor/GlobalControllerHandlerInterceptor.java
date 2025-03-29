package com.application.paymentmidtransservice.app.interceptor;

import com.application.paymentmidtransservice.app.exception.BusinessException;
import com.application.paymentmidtransservice.domain.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerHandlerInterceptor {

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
