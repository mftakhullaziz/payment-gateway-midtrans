package com.application.paymentmidtransservice.deliver.rest;

import com.application.paymentmidtransservice.domain.dto.PaymentDto;
import com.application.paymentmidtransservice.domain.payload.PaymentRequest;
import com.application.paymentmidtransservice.middleware.MidtransGateway;
import com.application.paymentmidtransservice.middleware.PaymentMidtransResponse;
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

    private final MidtransGateway midtransGateway;

    @PostMapping(value = "payment/create-transaction")
    public ResponseEntity<PaymentMidtransResponse> createPayment(@RequestBody PaymentRequest request) {
        PaymentDto paymentDto = PaymentDto.builder()
            .paymentTypes(request.getPaymentType())
            .bankType(request.getBankType())
            .orderId(request.getOrderId())
            .totalPrice(request.getTotalPrice())
            .build();
        return ResponseEntity.ok(midtransGateway.executePaymentMidtrans(paymentDto));
    }

}
