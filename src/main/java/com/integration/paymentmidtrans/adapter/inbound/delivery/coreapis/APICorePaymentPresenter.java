package com.integration.paymentmidtrans.adapter.inbound.delivery.coreapis;

import com.integration.paymentmidtrans.adapter.inbound.delivery.coreapis.response.VAChargeResponse;
import com.integration.paymentmidtrans.ports.inbound.presenter.iCoreAPIPaymentPresenter;
import com.integration.paymentmidtrans.shared.payload.Response;
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
