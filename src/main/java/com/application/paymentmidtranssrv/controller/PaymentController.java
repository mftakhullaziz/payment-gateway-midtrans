package com.application.paymentmidtranssrv.controller;

import com.application.paymentmidtranssrv.core.usecase.CCDCUsecase;
import com.application.paymentmidtranssrv.core.usecase.SnapTransferUsecase;
import com.application.paymentmidtranssrv.core.usecase.VaTransferUsecase;
import com.application.paymentmidtranssrv.domain.request.PaymentRequest;
import com.application.paymentmidtranssrv.domain.response.PaymentResponse;
import com.application.paymentmidtranssrv.domain.response.Response;
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

    private final VaTransferUsecase vaTransferUsecase;
    private final CCDCUsecase ccdcUsecase;
    private final SnapTransferUsecase snapTransferUsecase;

    @PostMapping("va-transfer")
    public ResponseEntity<Response<PaymentResponse>> vaTransferPayment(@Valid @RequestBody PaymentRequest request) {
        PaymentResponse paymentResponse = vaTransferUsecase.vaTransferPayment(request);
        Response<PaymentResponse> response = Response.created(paymentResponse);
        return ResponseEntity.ok(response);
    }

    @PostMapping("ccdc-transfer")
    public ResponseEntity<Response<PaymentResponse>> ccdcPayment(@Valid @RequestBody PaymentRequest request) {
        PaymentResponse paymentResponse = ccdcUsecase.ccdcTransferPayment(request);
        Response<PaymentResponse> response = Response.created(paymentResponse);
        return ResponseEntity.ok(response);
    }

    @PostMapping("snap-transfer")
    public ResponseEntity<Response<PaymentResponse>> snapPayment(@Valid @RequestBody PaymentRequest request) {
        PaymentResponse paymentResponse = snapTransferUsecase.snapTransferPayment(request);
        Response<PaymentResponse> response = Response.created(paymentResponse);
        return ResponseEntity.ok(response);
    }

}
