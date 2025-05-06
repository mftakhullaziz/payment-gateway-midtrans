package com.integration.paymentmidtrans.adapter.inbound.delivery.coreapis;

import com.integration.paymentmidtrans.adapter.inbound.delivery.coreapis.response.VAChargeResponse;
import com.integration.paymentmidtrans.ports.inbound.presenter.iCoreAPIPaymentPresenter;
import com.integration.paymentmidtrans.shared.payload.Response;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class APICorePaymentPresenter implements iCoreAPIPaymentPresenter {

  private ResponseEntity<Response<VAChargeResponse>> viewVAChargeResponse;

  @Override
  public void vaPaymentResponse(VAChargeResponse vaChargeResponse) {
    this.viewVAChargeResponse = Response.create(vaChargeResponse);
  }

}
