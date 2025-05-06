package com.integration.paymentmidtrans.ports.inbound.presenter;

import com.integration.paymentmidtrans.adapter.inbound.delivery.coreapis.response.VAChargeResponse;
import com.integration.paymentmidtrans.shared.payload.Response;
import org.springframework.http.ResponseEntity;

public interface iCoreAPIPaymentPresenter {
  void vaPaymentResponse(VAChargeResponse vaChargeResponse);
}
