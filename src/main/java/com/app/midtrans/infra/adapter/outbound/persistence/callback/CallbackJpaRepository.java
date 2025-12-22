package com.app.midtrans.infra.adapter.outbound.persistence.callback;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CallbackJpaRepository extends JpaRepository<CallbackEntity, Long> {
}
