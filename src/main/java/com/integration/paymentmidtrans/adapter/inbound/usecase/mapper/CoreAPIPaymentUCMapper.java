package com.integration.paymentmidtrans.adapter.inbound.usecase.mapper;

import com.integration.paymentmidtrans.adapter.inbound.delivery.coreapis.request.VAChargeRequest;
import com.integration.paymentmidtrans.shared.dto.coreapis.VaTransferDTO;

public class CoreAPIPaymentUCMapper {

  public static VaTransferDTO vaTransferMapper(VAChargeRequest vaChargeRequest) {
    return VaTransferDTO.builder().build();
  }

}
