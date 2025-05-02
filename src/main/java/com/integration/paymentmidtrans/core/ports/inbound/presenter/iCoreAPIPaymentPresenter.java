package com.integration.paymentmidtrans.core.ports.inbound.presenter;

import com.integration.paymentmidtrans.adapter.inbound.delivery.coreapis.response.VAChargeResponse;
import com.integration.paymentmidtrans.shared.payload.Response;
import org.springframework.http.ResponseEntity;

public interface iCoreAPIPaymentPresenter {
  void vaPaymentResponse(VAChargeResponse vaChargeResponse);
}
