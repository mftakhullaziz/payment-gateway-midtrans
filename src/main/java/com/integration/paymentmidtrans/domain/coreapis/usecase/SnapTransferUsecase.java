package com.integration.paymentmidtrans.domain.coreapis.usecase;

import com.integration.paymentmidtrans.shared.annotation.Usecase;
import com.integration.paymentmidtrans.domain.coreapis.request.PaymentRequest;
import com.integration.paymentmidtrans.domain.coreapis.response.PaymentResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Usecase
@RequiredArgsConstructor
public class SnapTransferUsecase {
    public PaymentResponse snapTransferPayment(@Valid PaymentRequest request) {
        return new PaymentResponse();
    }
}
