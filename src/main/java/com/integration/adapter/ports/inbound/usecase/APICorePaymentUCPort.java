package com.integration.adapter.ports.inbound.usecase;

import com.integration.adapter.inbound.delivery.coreapis.APICorePaymentPresenter;
import com.integration.adapter.inbound.delivery.coreapis.request.VAChargeRequest;

public interface APICorePaymentUCPort {
  void vaExecutor(VAChargeRequest vaChargeRequest, APICorePaymentPresenter presenter);
}
