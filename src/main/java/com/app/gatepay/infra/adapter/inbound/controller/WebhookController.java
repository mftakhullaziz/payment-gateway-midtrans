package com.app.gatepay.infra.adapter.inbound.controller;

import com.app.gatepay.application.usecase.v2.HandleWebhookUseCase;
import com.app.gatepay.infra.adapter.inbound.request.VaTransferNotifyRequest;
import com.app.gatepay.shared.enums.PaymentProvider;
import com.app.gatepay.shared.payload.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Generic webhook receiver (v2).
 *
 * Phase 1: MIDTRANS webhook payload is supported by reusing existing VA notify DTO.
 */
@RestController
@RequestMapping("/api/v2/webhooks")
@RequiredArgsConstructor
public class WebhookController {

    private final HandleWebhookUseCase handleWebhookUseCase;

    @PostMapping("/{provider}")
    public ResponseEntity<Response<Void>> webhook(
        @PathVariable PaymentProvider provider,
        @RequestBody VaTransferNotifyRequest request
    ) {
        // Phase 1: support MIDTRANS only. Other providers will have their own DTO/parser.
        if (provider != PaymentProvider.MIDTRANS) {
            throw new UnsupportedOperationException("Provider webhook not implemented yet: " + provider);
        }

        handleWebhookUseCase.execute(request, null);
        return Response.noContent();
    }
}
