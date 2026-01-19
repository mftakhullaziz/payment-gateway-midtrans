package com.app.gatepay.domain.callback;

public interface CallbackPersistencePort {
    void saveCallback(Callback callback);
}
