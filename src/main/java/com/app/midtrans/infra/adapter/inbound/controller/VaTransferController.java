package com.app.midtrans.infra.adapter.inbound.controller;

import com.app.midtrans.application.usecase.VaNotifyUseCase;
import com.app.midtrans.infra.adapter.inbound.request.VaTransferRequest;
import com.app.midtrans.infra.adapter.inbound.response.VaTransferResponse;
import com.app.midtrans.application.usecase.VaPaymentUseCase;
import com.app.midtrans.infra.adapter.inbound.request.VaTransferNotifyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1.0/virtual-account")
@RequiredArgsConstructor
public class VaTransferController {

    private final VaPaymentUseCase vaPaymentUseCase;
    private final VaNotifyUseCase vaNotifyUseCase;

    @PostMapping("/payment")
    public ResponseEntity<VaTransferResponse> createVaPayment(
        @RequestBody VaTransferRequest request
    ) {
        VaTransferResponse response = new VaTransferResponse();
        vaPaymentUseCase.execute(request, response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/notify")
    public ResponseEntity<Void> notifyVA(@RequestBody VaTransferNotifyRequest request) {
        vaNotifyUseCase.execute(request, null);
        return ResponseEntity.ok().build();
    }

    //@PostMapping("/payment")
    //    public ResponseEntity<Void> handleVaPaymentCallback(@Valid @RequestBody VaTransferCallbackRequest request) {
    //        log.info("Received payment notification: {}", JsonUtils.toJson(request));
    //        // 1. Validate headers (signature verification, timestamp check, etc.)
    //        // validateSignature(signature, timestamp, requestBody, endpoint, partnerId, externalId);
    //
    //        // 2. Process the payment notification
    //        // Save the payment details, update order/payment status, etc.
    //        // paymentCallbackUsecase.process(request);
    //        callbackNotificationUsecase.handleCallbackNotify(request);
    //
    //        // 3. Always return 200 OK (Midtrans expects it)
    //        return ResponseEntity.ok().build();
    //    }
}
