package com.integration.paymentmidtrans.domain.coreapis;

import com.integration.paymentmidtrans.domain.coreapis.usecase.CCDCUsecase;
import com.integration.paymentmidtrans.domain.coreapis.usecase.SnapTransferUsecase;
import com.integration.paymentmidtrans.domain.coreapis.request.VAChargeRequest;
import com.integration.paymentmidtrans.domain.coreapis.response.VAChargeResponse;
import com.integration.paymentmidtrans.domain.coreapis.usecase.VAChargeUsecase;
import com.integration.paymentmidtrans.domain.coreapis.request.PaymentRequest;
import com.integration.paymentmidtrans.domain.coreapis.response.PaymentResponse;
import com.integration.paymentmidtrans.domain.coreapis.usecase.VaTransferUsecase;
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
public class CoreapiController {

  private final VaTransferUsecase vaTransferUsecase;
  private final CCDCUsecase ccdcUsecase;
  private final SnapTransferUsecase snapTransferUsecase;
  private final VAChargeUsecase vaChargeUsecase;

  public static final String VIRTUAL_ACCOUNT_API = "/coreapi/virtual-account";

  @PostMapping(value = VIRTUAL_ACCOUNT_API)
  public ResponseEntity<Response<VAChargeResponse>> vaChargeCoreapi(@RequestBody VAChargeRequest vaChargeRequest) {
    VAChargeResponse vaTransferResponse = vaChargeUsecase.vaChargeExecutor(vaChargeRequest);
    return Response.create(vaTransferResponse);
  }

  @PostMapping(value = VIRTUAL_ACCOUNT_API)
  public ResponseEntity<Response<PaymentResponse>> virtualAccountCharge(
    @Valid @RequestBody PaymentRequest request)
  {
    PaymentResponse paymentResponse = vaTransferUsecase.vaTransferPayment(request);
    return Response.create(paymentResponse);
  }

  @PostMapping("ccdc/charge")
  public ResponseEntity<Response<PaymentResponse>> ccdcPayment(@Valid @RequestBody PaymentRequest request) {
    PaymentResponse paymentResponse = ccdcUsecase.ccdcTransferPayment(request);
    return Response.create(paymentResponse);
  }

  @PostMapping("snap-transfer")
  public ResponseEntity<Response<PaymentResponse>> snapPayment(@Valid @RequestBody PaymentRequest request) {
    PaymentResponse paymentResponse = snapTransferUsecase.snapTransferPayment(request);
    return Response.create(paymentResponse);
  }

}
