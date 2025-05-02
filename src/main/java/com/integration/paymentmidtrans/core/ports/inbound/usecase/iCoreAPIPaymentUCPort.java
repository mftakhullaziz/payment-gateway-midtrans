package com.integration.paymentmidtrans.core.ports.inbound.usecase;

import com.integration.paymentmidtrans.adapter.inbound.delivery.coreapis.APICorePaymentPresenter;

public interface iCoreAPIPaymentUCPort {
  void paymentCoreAPIMidtransExecutor(String paymentId, APICorePaymentPresenter presenter);
}
