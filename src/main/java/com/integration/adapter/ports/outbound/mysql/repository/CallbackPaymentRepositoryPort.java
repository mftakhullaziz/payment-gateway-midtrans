package com.integration.adapter.ports.outbound.mysql.repository;

import com.integration.adapter.outbound.mysql.entity.PaymentCallbackEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CallbackPaymentRepositoryPort extends JpaRepository<PaymentCallbackEntity, Long> {
}
