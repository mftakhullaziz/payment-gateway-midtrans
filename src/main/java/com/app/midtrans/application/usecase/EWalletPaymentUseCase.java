package com.app.midtrans.application.usecase;

import com.app.midtrans.application.UseCaseExecutor;
import com.app.midtrans.domain.payment.PaymentGatewayPort;
import com.app.midtrans.domain.payment.PaymentService;
import com.app.midtrans.infra.adapter.inbound.request.EWalletTransferRequest;
import com.app.midtrans.infra.adapter.inbound.response.EWalletTransferResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EWalletPaymentUseCase
    extends UseCaseExecutor<EWalletTransferRequest, EWalletTransferResponse> {

    private final PaymentGatewayPort paymentGatewayPort;
    private final PaymentService paymentService;

    @Override
    public void execute(EWalletTransferRequest input, EWalletTransferResponse output) {

    }

    private void buildResponse(EWalletTransferResponse output) {

    }

}
