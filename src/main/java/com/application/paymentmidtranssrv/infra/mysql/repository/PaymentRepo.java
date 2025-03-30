package com.application.paymentmidtranssrv.infra.mysql.repository;

import com.application.paymentmidtranssrv.infra.mysql.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepo extends JpaRepository<PaymentEntity, Integer> {
}
