package com.app.gatepay.application.usecase.v2;

import com.app.gatepay.application.UseCaseExecutor;
import com.app.gatepay.application.usecase.VaNotifyUseCase;
import com.app.gatepay.infra.adapter.inbound.request.VaTransferNotifyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * V2 webhook handler facade.
 *
 * Phase 1: delegates to existing Midtrans VA notify use case.
 */
@Component
@RequiredArgsConstructor
public class HandleWebhookUseCase extends UseCaseExecutor<VaTransferNotifyRequest, Void> {

    private final VaNotifyUseCase vaNotifyUseCase;

    @Override
    public void execute(VaTransferNotifyRequest input, Void output) {
        vaNotifyUseCase.execute(input, output);
    }
}

