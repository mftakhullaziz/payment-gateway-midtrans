package com.integration.paymentmidtrans.adapter.inbound.delivery.coreapis;

import com.integration.paymentmidtrans.adapter.inbound.delivery.coreapis.response.VAChargeResponse;
import com.integration.paymentmidtrans.core.ports.inbound.presenter.iCoreAPIPaymentPresenter;
import com.integration.paymentmidtrans.shared.payload.Response;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class APICorePaymentPresenter implements iCoreAPIPaymentPresenter {

  private ResponseEntity<Response<VAChargeResponse>> view;

  @Override
  public void vaPaymentResponse(VAChargeResponse vaChargeResponse) {
    this.view = Response.create(vaChargeResponse);
  }

}
