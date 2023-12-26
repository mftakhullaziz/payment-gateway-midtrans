package com.application.paymentmidtransservice.deliver.rest;

import com.application.paymentmidtransservice.deliver.usecase.PaymentUsecase;
import com.application.paymentmidtransservice.domain.request.PaymentRequest;
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
public class PaymentRest {

    private final PaymentUsecase paymentUsecase;

    @PostMapping(value = "payment/create-transaction")
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody PaymentRequest request) {
        PaymentResponse response = paymentUsecase.executePaymentTransaction(request);
        return ResponseEntity.ok(response);
    }

}
