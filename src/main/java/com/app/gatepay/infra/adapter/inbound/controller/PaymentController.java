package com.app.gatepay.infra.adapter.inbound.controller;

import com.app.gatepay.api.v2.payment.dto.CreatePaymentRequest;
import com.app.gatepay.api.v2.payment.dto.CreatePaymentResponse;
import com.app.gatepay.application.usecase.v2.CreatePaymentUseCase;
import com.app.gatepay.shared.payload.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * V2 payment API (provider-agnostic).
 *
 * Placement follows hexagonal convention for inbound adapters.
 */
@RestController
@RequestMapping("/api/v2/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final CreatePaymentUseCase createPaymentUseCase;

    @PostMapping
    public ResponseEntity<Response<CreatePaymentResponse>> create(
        @RequestBody CreatePaymentRequest request
    ) {
        CreatePaymentResponse response = new CreatePaymentResponse();
        createPaymentUseCase.execute(request, response);
        return Response.ok(response);
    }
}

