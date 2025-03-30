package com.application.paymentmidtranssrv.core.usecase;

import com.application.paymentmidtranssrv.app.annotation.Usecase;
import com.application.paymentmidtranssrv.domain.request.PaymentRequest;
import com.application.paymentmidtranssrv.domain.response.PaymentResponse;
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
