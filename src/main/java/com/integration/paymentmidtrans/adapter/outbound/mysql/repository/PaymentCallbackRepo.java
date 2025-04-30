package com.integration.paymentmidtrans.adapter.outbound.mysql.repository;

import com.integration.paymentmidtrans.adapter.outbound.mysql.entity.PaymentCallbackEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentCallbackRepo extends JpaRepository<PaymentCallbackEntity, Long> {
}
