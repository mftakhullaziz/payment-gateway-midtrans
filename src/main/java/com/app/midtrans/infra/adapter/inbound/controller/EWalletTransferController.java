package com.app.midtrans.infra.adapter.inbound.controller;

import com.app.midtrans.application.usecase.EWalletPaymentUseCase;
import com.app.midtrans.infra.adapter.inbound.request.EWalletTransferRequest;
import com.app.midtrans.infra.adapter.inbound.response.EWalletTransferResponse;
import com.app.midtrans.shared.payload.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1.0/ewallet")
@RequiredArgsConstructor
public class EWalletTransferController {

    private final EWalletPaymentUseCase ewalletPaymentUseCase;

    @PostMapping("/transfer")
    public ResponseEntity<Response<EWalletTransferResponse>> transferEWallet(
        @RequestBody EWalletTransferRequest request
    ) {
        EWalletTransferResponse response = new EWalletTransferResponse();
        ewalletPaymentUseCase.execute(request, response);
        return Response.ok(response);
    }

}
