package com.application.paymentmidtranssrv.delivery.coreapi;

import com.application.paymentmidtranssrv.core.usecase.coreapi.VATransferUsecase;
import com.application.paymentmidtranssrv.delivery.coreapi.request.VATransferRequest;
import com.application.paymentmidtranssrv.delivery.coreapi.response.VATransferResponse;
import com.application.paymentmidtranssrv.domain.response.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@Tag(name = "COREAPI-Payment")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/payments")
public class CoreAPIPaymentController {

    private final VATransferUsecase vaTransferUsecase;

    // API List
    public static final String VIRTUAL_ACCOUNT_API = "/virtual-account";

    @PostMapping(value = VIRTUAL_ACCOUNT_API)
    public ResponseEntity<Response<VATransferResponse>> virtualAccountCharge(
        @RequestBody VATransferRequest vaTransferRequest)
    {
        VATransferResponse vaTransferResponse = vaTransferUsecase.vaExecutor(vaTransferRequest);
        return ResponseEntity.ok(Response.created(vaTransferResponse));
    }
}
