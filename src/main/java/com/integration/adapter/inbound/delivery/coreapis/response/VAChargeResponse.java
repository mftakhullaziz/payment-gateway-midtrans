package com.integration.adapter.inbound.delivery.coreapis.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VAChargeResponse {
  private String paymentType;
  private String orderId;
  private String transactionId;
}
