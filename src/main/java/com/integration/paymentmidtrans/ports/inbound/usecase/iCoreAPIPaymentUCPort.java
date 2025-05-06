package com.integration.paymentmidtrans.ports.inbound.usecase;

import com.integration.paymentmidtrans.adapter.inbound.delivery.coreapis.APICorePaymentPresenter;
import com.integration.paymentmidtrans.adapter.inbound.delivery.coreapis.request.VAChargeRequest;

public interface iCoreAPIPaymentUCPort {
  void vaExecutor(VAChargeRequest vaChargeRequest, APICorePaymentPresenter presenter);
}
