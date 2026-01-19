package com.app.gatepay.infra.adapter.outbound.persistence.callback;

import com.app.gatepay.domain.callback.Callback;
import com.app.gatepay.domain.callback.CallbackPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CallbackPersistenceAdapter implements CallbackPersistencePort {

    private final CallbackJpaRepository callbackJpaRepository;

    @Override
    public void saveCallback(Callback callback) {
        CallbackEntity callbackEntity = new CallbackEntity();
        callbackEntity.setOrderId(callback.getOrderId());
        callbackEntity.setTransactionId(callback.getTransactionId());
        callbackEntity.setDataCallbacks(callback.getDataCallbacks());
        callbackJpaRepository.save(callbackEntity);
    }

}
