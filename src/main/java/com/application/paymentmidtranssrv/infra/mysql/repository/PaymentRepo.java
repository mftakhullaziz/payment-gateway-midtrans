package com.application.paymentmidtranssrv.infra.mysql.repository;

import com.application.paymentmidtranssrv.infra.mysql.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepo extends JpaRepository<PaymentEntity, Integer> {
    Optional<PaymentEntity> findByOrderId(String orderId);

    Optional<PaymentEntity> findByOrderIdAndIdAndCustomerId(String orderId, Long id, Long customerId);
}
