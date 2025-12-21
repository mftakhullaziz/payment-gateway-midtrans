package com.app.midtrans.infra.adapter.outbound.persistence.payment;

import com.app.midtrans.domain.payment.Payment;
import com.app.midtrans.domain.payment.PaymentPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentPersistenceAdapter implements PaymentPersistencePort {

    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public void savePayment(Payment payment) {

    }

}
