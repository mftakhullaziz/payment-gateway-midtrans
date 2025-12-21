package com.app.midtrans.domain.payment;

public interface PaymentPersistencePort {
    void savePayment(Payment payment);
}
