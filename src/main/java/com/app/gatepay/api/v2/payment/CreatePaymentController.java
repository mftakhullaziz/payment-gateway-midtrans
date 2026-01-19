package com.app.gatepay.api.v2.payment;

import com.app.gatepay.api.v2.payment.dto.CreatePaymentRequest;
import com.app.gatepay.api.v2.payment.dto.CreatePaymentResponse;
import com.app.gatepay.shared.payload.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

/**
 * @deprecated Controllers should live under infra.adapter.inbound.controller.
 * This class is kept only for source compatibility.
 */
@Deprecated
@RequiredArgsConstructor
public class CreatePaymentController {

    private final com.app.gatepay.infra.adapter.inbound.controller.PaymentController delegate;

    public ResponseEntity<Response<CreatePaymentResponse>> create(
        CreatePaymentRequest request
    ) {
        return delegate.create(request);
    }
}
