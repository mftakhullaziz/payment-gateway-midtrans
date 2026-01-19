package com.app.gatepay.domain.payment;

public interface PaymentPersistencePort {
    void savePayment(Payment payment);
    void updatePayment(String transactionStatus, String orderId, String transactionId);
    Long findCustomerId(String orderId, String transactionId);
}
