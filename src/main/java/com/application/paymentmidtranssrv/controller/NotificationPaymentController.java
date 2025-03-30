package com.application.paymentmidtranssrv.controller;

import com.application.paymentmidtranssrv.core.usecase.CallbackNotificationUsecase;
import com.application.paymentmidtranssrv.domain.request.VaTransferCallbackRequest;
import com.application.paymentmidtranssrv.utility.JsonUtility;
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
@Tag(name = "Payment Notifications")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/notification")
public class NotificationPaymentController {

    private final CallbackNotificationUsecase callbackNotificationUsecase;

    @PostMapping("/payment")
    public ResponseEntity<Void> handleVaPaymentCallback(@Valid @RequestBody VaTransferCallbackRequest request) {
        log.info("Received payment notification: {}", JsonUtility.toJson(request));
        // 1. Validate headers (signature verification, timestamp check, etc.)
        // validateSignature(signature, timestamp, requestBody, endpoint, partnerId, externalId);

        // 2. Process the payment notification
        // Save the payment details, update order/payment status, etc.
        // paymentCallbackUsecase.process(request);
        callbackNotificationUsecase.handleCallbackNotify(request);

        // When Success payment status send email

        // 3. Always return 200 OK (Midtrans expects it)
        return ResponseEntity.ok().build();
    }
}
