package com.application.paymentmidtranssrv.core.gateway.impl;

import com.application.paymentmidtranssrv.app.annotation.Gateway;
import com.application.paymentmidtranssrv.app.exception.BusinessException;
import com.application.paymentmidtranssrv.infra.mysql.entity.PaymentEntity;
import com.application.paymentmidtranssrv.infra.mysql.repository.PaymentRepo;
import com.application.paymentmidtranssrv.core.gateway.PaymentGateway;
import com.application.paymentmidtranssrv.domain.model.Payment;
import com.application.paymentmidtranssrv.domain.request.CreatePaymentRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Gateway
@RequiredArgsConstructor
public class PaymentGatewayImpl implements PaymentGateway {

    private final PaymentRepo paymentRepo;

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

        PaymentEntity paymentEntity = paymentRepo.saveAndFlush(constructPaymentEntity);
        return constructPayment(paymentEntity);
    }

    @Override
    public Payment findByOrderId(String orderId) {
        return paymentRepo.findByOrderId(orderId)
            .map(PaymentGatewayImpl::constructPayment)
            .orElse(null);
    }

    @Override
    public void updatePayment(String transactionStatus,
                              String orderId,
                              String transactionId) {
        paymentRepo.findByOrderIdAndTransactionId(orderId, transactionId)
            .ifPresent(payment -> {
                payment.setTransactionStatus(transactionStatus);
                paymentRepo.save(payment);
            });
    }

    @Override
    public Long findCustomerIdByOrderIdAndTransactionId(String orderId, String transactionId) {
        return paymentRepo.findByOrderIdAndTransactionId(orderId, transactionId)
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
