package com.integration.paymentmidtrans.adapter.inbound.usecase;

import com.integration.paymentmidtrans.adapter.inbound.delivery.coreapis.request.VAChargeRequest;
import com.integration.paymentmidtrans.adapter.inbound.delivery.coreapis.response.VAChargeResponse;
import com.integration.paymentmidtrans.shared.annotation.Usecase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Usecase
@RequiredArgsConstructor
public class VAChargeUsecase {


  public VAChargeResponse vaChargeExecutor(VAChargeRequest vaTransferRequest) {
    return null;
  }
}
