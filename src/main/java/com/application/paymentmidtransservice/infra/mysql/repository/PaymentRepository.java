package com.application.paymentmidtransservice.infra.mysql.repository;

import com.application.paymentmidtransservice.infra.mysql.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Integer> {
}
