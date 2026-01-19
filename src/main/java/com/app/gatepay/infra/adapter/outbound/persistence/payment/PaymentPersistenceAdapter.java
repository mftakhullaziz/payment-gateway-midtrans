package com.app.gatepay.infra.adapter.outbound.persistence.payment;

import com.app.gatepay.domain.payment.Payment;
import com.app.gatepay.domain.payment.PaymentPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@RequiredArgsConstructor
public class PaymentPersistenceAdapter implements PaymentPersistencePort {

    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public void savePayment(Payment payment) {
        PaymentEntity paymentEntity = toPaymentEntity(payment);
        paymentJpaRepository.save(paymentEntity);
    }

    @Override
    public void updatePayment(String transactionStatus, String orderId, String transactionId) {
        paymentJpaRepository.findByOrderIdAndProviderTransactionId(orderId, transactionId)
            .ifPresent(payment -> {
                payment.setStatus(transactionStatus);
                paymentJpaRepository.save(payment);
            });
    }

    @Override
    public Long findCustomerId(String orderId, String transactionId) {
        return paymentJpaRepository.findByOrderIdAndProviderTransactionId(orderId, transactionId)
            .map(PaymentEntity::getCustomerId)
            .orElse(null);
    }

    private PaymentEntity toPaymentEntity(Payment payment) {
        return PaymentEntity.builder()
            .customerId(payment.getCustomerId())
            .orderId(payment.getOrderId())
            .provider(payment.getProvider())
            .providerTransactionId(payment.getTransactionId())
            .amount(payment.getTotalAmount())
            .currency(payment.getCurrency())
            .status(payment.getTransactionStatus())
            .paymentTime(Timestamp.valueOf(payment.getTransactionTime()))
            .channel(payment.getVaChannel().getLabel())
            .method(payment.getPaymentType())
            .bankCode(payment.getVaBank())
            .referenceNumber(resolveReferenceNumber(payment))
            .build();
    }

    private String resolveReferenceNumber(Payment payment) {
        if (payment.getBillKey() != null) {
            return payment.getBillKey();
        }
        return payment.getVaNumber();
    }

}
