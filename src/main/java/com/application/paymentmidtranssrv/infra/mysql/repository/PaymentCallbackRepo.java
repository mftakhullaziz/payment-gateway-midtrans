package com.application.paymentmidtranssrv.infra.mysql.repository;

import com.application.paymentmidtranssrv.infra.mysql.entity.PaymentCallbackEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentCallbackRepo extends JpaRepository<PaymentCallbackEntity, Long> {
}
