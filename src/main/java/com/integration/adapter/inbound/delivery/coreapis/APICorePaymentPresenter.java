package com.integration.adapter.inbound.delivery.coreapis;

import com.integration.adapter.inbound.delivery.coreapis.response.VAChargeResponse;
import com.integration.adapter.ports.inbound.presenter.iCoreAPIPaymentPresenter;
import com.app.midtrans.shared.payload.Response;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class APICorePaymentPresenter implements iCoreAPIPaymentPresenter {

  private ResponseEntity<Response<VAChargeResponse>> presentVAChargeResponse;

  @Override
  public void vaPaymentResponse(VAChargeResponse vaChargeResponse) {
    this.presentVAChargeResponse = Response.create(vaChargeResponse);
  }

}
