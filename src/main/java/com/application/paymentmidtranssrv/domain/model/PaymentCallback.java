package com.application.paymentmidtranssrv.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCallback {
    private Long paymentId;
    private Long customerId;
    private String callbacks;
}
