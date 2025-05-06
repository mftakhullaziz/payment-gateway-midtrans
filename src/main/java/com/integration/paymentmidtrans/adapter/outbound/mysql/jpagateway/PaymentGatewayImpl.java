package com.integration.paymentmidtrans.adapter.outbound.mysql.jpagateway;

import com.integration.paymentmidtrans.shared.annotation.Gateway;
import com.integration.paymentmidtrans.adapter.outbound.mysql.entity.PaymentEntity;
import com.integration.paymentmidtrans.ports.outbound.mysql.repository.PaymentRepositoryPort;
import com.integration.paymentmidtrans.ports.outbound.mysql.jpa.PaymentJPAOutboundPort;
import com.integration.paymentmidtrans.shared.dto.coreapis.Payment;
import com.integration.paymentmidtrans.adapter.inbound.delivery.coreapis.request.CreatePaymentRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Gateway
@RequiredArgsConstructor
public class PaymentGatewayImpl implements PaymentJPAOutboundPort {

    private final PaymentRepositoryPort paymentRepositoryPort;

    @Override
    public Payment savePaymentTransaction(CreatePaymentRequest request) {
        PaymentEntity constructPaymentEntity = PaymentEntity.builder()
            .orderId(request.getOrderId())
            .customerId(request.getCustomerId())
            .transactionId(request.getTransactionId())
            .merchantId(request.getMerchantId())
            .grossAmount(request.getGrossAmount())
            .currency(request.getCurrency())
            .transactionTime(request.getTransactionTime())
            .transactionStatus(request.getTransactionStatus())
            .expiryTime(request.getExpiryTime())
            .fraudStatus(request.getFraudStatus())
            .paymentType(request.getPaymentType())
            .paymentMethod(request.getPaymentMethod())
            .paymentVaNumbers(request.getPaymentVaNumbers())
            .totalTax(request.getTotalTax())
            .totalDiscount(request.getTotalDiscount())
            .totalPrice(request.getTotalPrice())
            .build();

        PaymentEntity paymentEntity = paymentRepositoryPort.saveAndFlush(constructPaymentEntity);
        return constructPayment(paymentEntity);
    }

    @Override
    public Payment findByOrderId(String orderId) {
        return paymentRepositoryPort.findByOrderId(orderId)
            .map(PaymentGatewayImpl::constructPayment)
            .orElse(null);
    }

    @Override
    public void updatePayment(String transactionStatus,
                              String orderId,
                              String transactionId) {
        paymentRepositoryPort.findByOrderIdAndTransactionId(orderId, transactionId)
            .ifPresent(payment -> {
                payment.setTransactionStatus(transactionStatus);
                paymentRepositoryPort.save(payment);
            });
    }

    @Override
    public Long findCustomerIdByOrderIdAndTransactionId(String orderId, String transactionId) {
        return paymentRepositoryPort.findByOrderIdAndTransactionId(orderId, transactionId)
            .map(PaymentEntity::getCustomerId)
            .orElse(null);
    }

    private static Payment constructPayment(PaymentEntity paymentEntity) {
        Payment payment = new Payment();
        payment.setPaymentId(paymentEntity.getId());
        payment.setCustomerId(paymentEntity.getCustomerId());
        payment.setOrderId(paymentEntity.getOrderId());
        payment.setTransactionId(paymentEntity.getTransactionId());
        payment.setMerchantId(paymentEntity.getMerchantId());
        payment.setGrossAmount(paymentEntity.getGrossAmount());
        payment.setCurrency(paymentEntity.getCurrency());
        payment.setTransactionTime(paymentEntity.getTransactionTime());
        payment.setTransactionStatus(paymentEntity.getTransactionStatus());
        payment.setExpiryTime(paymentEntity.getExpiryTime());
        payment.setFraudStatus(paymentEntity.getFraudStatus());
        payment.setPaymentType(paymentEntity.getPaymentType());
        payment.setPaymentMethod(paymentEntity.getPaymentMethod());
        payment.setPaymentVaNumbers(paymentEntity.getPaymentVaNumbers());
        payment.setTotalPrice(paymentEntity.getTotalPrice());
        payment.setTotalTax(paymentEntity.getTotalTax());
        payment.setTotalDiscount(paymentEntity.getTotalDiscount());
        return payment;
    }
}
