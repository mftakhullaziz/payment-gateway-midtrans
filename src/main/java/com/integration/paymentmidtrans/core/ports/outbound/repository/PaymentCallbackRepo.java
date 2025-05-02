package com.integration.paymentmidtrans.core.ports.outbound.repository;

import com.integration.paymentmidtrans.adapter.outbound.mysql.entity.PaymentCallbackEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentCallbackRepo extends JpaRepository<PaymentCallbackEntity, Long> {
}
