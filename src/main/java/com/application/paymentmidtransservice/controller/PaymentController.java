package com.application.paymentmidtransservice.controller;

import com.application.paymentmidtransservice.core.usecase.PaymentUsecase;
import com.application.paymentmidtransservice.domain.request.PaymentRequest;
import com.application.paymentmidtransservice.domain.response.DefaultResponse;
import com.application.paymentmidtransservice.domain.response.PaymentResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@Tag(name = "Payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentUsecase paymentUsecase;

    @PostMapping(value = "payment/create-transaction")
    public ResponseEntity<DefaultResponse<PaymentResponse>> createPayment(@RequestBody PaymentRequest request) {
        PaymentResponse response = paymentUsecase.executePaymentTransaction(request);
        DefaultResponse<PaymentResponse> defaultResponse = new DefaultResponse<>();
        defaultResponse.setData(response);
        defaultResponse.setSuccess(true);
        defaultResponse.setStatusCode(201);
        defaultResponse.setMessage("create transaction successfully!");
        return ResponseEntity.ok(defaultResponse);
    }

}
