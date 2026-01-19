package com.app.gatepay.infra.adapter.outbound.persistence.payment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentJpaRepository extends JpaRepository<PaymentEntity, Long> {
    Optional<PaymentEntity> findByOrderIdAndProviderTransactionId(String orderId, String providerTransactionId);
}
