package com.integration.adapter.inbound.usecase.mapper;

import com.integration.adapter.inbound.delivery.coreapis.request.VAChargeRequest;
import com.app.midtrans.shared.dto.coreapis.VaTransferDTO;

public class CoreAPIPaymentUCMapper {

  public static VaTransferDTO vaTransferMapper(VAChargeRequest vaChargeRequest) {
    return VaTransferDTO.builder().build();
  }

}
