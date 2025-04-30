package com.integration.paymentmidtrans.adapter.outbound.mysql.repository;

import com.integration.paymentmidtrans.adapter.outbound.mysql.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepo extends JpaRepository<PaymentEntity, Integer> {
    Optional<PaymentEntity> findByOrderId(String orderId);

    Optional<PaymentEntity> findByOrderIdAndIdAndCustomerId(String orderId, Long id, Long customerId);

    Optional<PaymentEntity> findByOrderIdAndTransactionId(String orderId, String transactionId);
}
