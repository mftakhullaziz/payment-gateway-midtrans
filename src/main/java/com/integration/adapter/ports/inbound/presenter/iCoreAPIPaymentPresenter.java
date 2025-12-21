package com.integration.adapter.ports.inbound.presenter;

import com.integration.adapter.inbound.delivery.coreapis.response.VAChargeResponse;

public interface iCoreAPIPaymentPresenter {
  void vaPaymentResponse(VAChargeResponse vaChargeResponse);
}
