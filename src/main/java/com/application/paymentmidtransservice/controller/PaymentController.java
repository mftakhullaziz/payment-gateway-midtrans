package com.application.paymentmidtransservice.controller;

import com.application.paymentmidtransservice.core.usecase.PaymentUsecase;
import com.application.paymentmidtransservice.domain.request.PaymentRequest;
import com.application.paymentmidtransservice.domain.response.DefaultResponse;
import com.application.paymentmidtransservice.domain.response.PaymentResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@Tag(name = "Payments")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/payment")
public class PaymentController {

    private final PaymentUsecase usecase;

    @PostMapping("va-transfer")
    public ResponseEntity<DefaultResponse<PaymentResponse>> vaTransferPayment(@Valid @RequestBody PaymentRequest request) {
        PaymentResponse response = usecase.vaTransferPayment(request);
        DefaultResponse<PaymentResponse> defaultResponse = new DefaultResponse<>();
        defaultResponse.setData(response);
        defaultResponse.setSuccess(true);
        defaultResponse.setStatusCode(201);
        defaultResponse.setMessage("create transaction successfully!");
        return ResponseEntity.ok(defaultResponse);
    }

    @PostMapping("ccdc-transfer")
    public ResponseEntity<DefaultResponse<PaymentResponse>> ccdcPayment(@Valid @RequestBody PaymentRequest request) {
        DefaultResponse<PaymentResponse> defaultResponse = new DefaultResponse<>();
        defaultResponse.setData(null);
        defaultResponse.setSuccess(true);
        defaultResponse.setStatusCode(201);
        defaultResponse.setMessage("create transaction successfully!");
        return ResponseEntity.ok(defaultResponse);
    }

}
