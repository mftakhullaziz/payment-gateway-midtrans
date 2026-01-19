package com.app.gatepay.infra.adapter.inbound.controller;

import com.app.gatepay.application.usecase.VaNotifyUseCase;
import com.app.gatepay.infra.adapter.inbound.request.VaTransferRequest;
import com.app.gatepay.infra.adapter.inbound.response.VaTransferResponse;
import com.app.gatepay.application.usecase.VaPaymentUseCase;
import com.app.gatepay.infra.adapter.inbound.request.VaTransferNotifyRequest;
import com.app.gatepay.shared.payload.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1.0/va")
@RequiredArgsConstructor
public class VaTransferController {

    private final VaPaymentUseCase vaPaymentUseCase;
    private final VaNotifyUseCase vaNotifyUseCase;

    @PostMapping("/transfer")
    public ResponseEntity<Response<VaTransferResponse>> transferVa(
        @RequestBody VaTransferRequest request
    ) {
        VaTransferResponse response = new VaTransferResponse();
        vaPaymentUseCase.execute(request, response);
        return Response.ok(response);
    }

    @PostMapping("/notify")
    public ResponseEntity<Response<Void>> notifyVa(
        @RequestBody VaTransferNotifyRequest request
    ) {
        vaNotifyUseCase.execute(request, null);
        return Response.noContent();
    }

}
