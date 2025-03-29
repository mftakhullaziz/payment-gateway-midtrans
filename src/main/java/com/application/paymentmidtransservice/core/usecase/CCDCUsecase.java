package com.application.paymentmidtransservice.core.usecase;

import com.application.paymentmidtransservice.app.annotation.Usecase;
import com.application.paymentmidtransservice.domain.request.PaymentRequest;
import com.application.paymentmidtransservice.domain.response.PaymentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Usecase
@RequiredArgsConstructor
public class CCDCUsecase {

    public PaymentResponse ccdcTransferPayment(PaymentRequest paymentRequest) {
        return null;
    }

}
