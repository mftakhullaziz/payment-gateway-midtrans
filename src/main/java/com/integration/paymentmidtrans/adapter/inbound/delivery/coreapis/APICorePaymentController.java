package com.integration.paymentmidtrans.adapter.inbound.delivery.coreapis;

import com.integration.paymentmidtrans.adapter.inbound.delivery.coreapis.request.PaymentRequest;
import com.integration.paymentmidtrans.adapter.inbound.delivery.coreapis.request.VAChargeRequest;
import com.integration.paymentmidtrans.adapter.inbound.delivery.coreapis.response.PaymentResponse;
import com.integration.paymentmidtrans.adapter.inbound.delivery.coreapis.response.VAChargeResponse;
import com.integration.paymentmidtrans.adapter.inbound.usecase.VaTransferUsecase;
import com.integration.paymentmidtrans.ports.inbound.usecase.APICorePaymentUCPort;
import com.integration.paymentmidtrans.shared.payload.Response;
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
@Tag(name = "Payment - coreapi midtrans")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/payment")
public class APICorePaymentController {

    // private final APICorePaymentUCPort apiCorePaymentUCPort;

    public static final String VIRTUAL_ACCOUNT_API = "/coreapi/virtual-account";
    public static final String VIRTUAL_ACCOUNT_API_OLD = "/coreapi/old/virtual-account";
    public static final String CCD_API = "/coreapi/ccdc";
    public static final String SNAP_TRANSFER_API = "/coreapi/snap-transfer";

    private final VaTransferUsecase vaTransferUsecase;

    // Old
    @PostMapping(value = VIRTUAL_ACCOUNT_API_OLD, consumes = "application/json", produces = "application/json")
    public ResponseEntity<PaymentResponse> vaChargeCoreapiOld(@RequestBody @Valid PaymentRequest paymentRequest) {
        PaymentResponse paymentResponse = vaTransferUsecase.vaTransferPayment(paymentRequest);
        return ResponseEntity.ok(paymentResponse);
    }

//    @PostMapping(value = VIRTUAL_ACCOUNT_API, consumes = "application/json", produces = "application/json")
//    public ResponseEntity<Response<VAChargeResponse>> vaChargeCoreapi(@RequestBody @Valid VAChargeRequest vaChargeRequest) {
//        APICorePaymentPresenter presenter = new APICorePaymentPresenter();
//        apiCorePaymentUCPort.vaExecutor(vaChargeRequest, presenter);
//        return presenter.getPresentVAChargeResponse();
//    }

}
