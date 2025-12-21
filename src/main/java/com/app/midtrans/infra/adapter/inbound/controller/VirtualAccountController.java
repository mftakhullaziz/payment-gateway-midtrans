package com.app.midtrans.infra.adapter.inbound.controller;

import com.app.midtrans.infra.adapter.inbound.request.VirtualAccountRequest;
import com.app.midtrans.infra.adapter.inbound.response.VirtualAccountResponse;
import com.app.midtrans.application.usecase.VirtualAccountUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1.0/virtual-account")
@RequiredArgsConstructor
public class VirtualAccountController {

    private final VirtualAccountUseCase virtualAccountUseCase;

    @PostMapping("/payment")
    public ResponseEntity<VirtualAccountResponse> createVaPayment(
        @RequestBody VirtualAccountRequest request
    ) {
        VirtualAccountResponse response = new VirtualAccountResponse();
        virtualAccountUseCase.execute(request, response);
        return ResponseEntity.ok(response);
    }

}
