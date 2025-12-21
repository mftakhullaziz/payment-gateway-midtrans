package com.app.midtrans.application.usecase;

import com.app.midtrans.application.UseCaseExecutor;
import com.app.midtrans.infra.adapter.inbound.request.VaTransferNotifyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VaNotifyUseCase extends UseCaseExecutor<VaTransferNotifyRequest, Void> {

    @Override
    public void execute(VaTransferNotifyRequest input, Void output) {

    }

}
