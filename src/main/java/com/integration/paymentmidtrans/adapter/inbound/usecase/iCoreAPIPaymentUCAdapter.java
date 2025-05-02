package com.integration.paymentmidtrans.adapter.inbound.usecase;

import com.integration.paymentmidtrans.adapter.inbound.delivery.coreapis.APICorePaymentPresenter;
import com.integration.paymentmidtrans.core.ports.inbound.usecase.iCoreAPIPaymentUCPort;
import com.integration.paymentmidtrans.shared.annotation.Usecase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Usecase
@RequiredArgsConstructor
public class iCoreAPIPaymentUCAdapter implements iCoreAPIPaymentUCPort {

  @Override
  public void paymentCoreAPIMidtransExecutor(String paymentId, APICorePaymentPresenter presenter) {
//    PaymentCoreMidtransResponse response = new PaymentCoreMidtransResponse();
//    presenter.present(response);
    presenter.vaPaymentResponse(null);
  }

}
