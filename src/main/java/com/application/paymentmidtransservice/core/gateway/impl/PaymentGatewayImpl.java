package com.application.paymentmidtransservice.core.gateway.impl;

import com.application.paymentmidtransservice.app.annotation.Gateway;
import com.application.paymentmidtransservice.infra.mysql.entity.PaymentEntity;
import com.application.paymentmidtransservice.infra.mysql.repository.PaymentRepo;
import com.application.paymentmidtransservice.core.gateway.PaymentGateway;
import com.application.paymentmidtransservice.domain.model.Payment;
import com.application.paymentmidtransservice.domain.request.CreatePaymentRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Gateway
@RequiredArgsConstructor
public class PaymentGatewayImpl implements PaymentGateway {

    private final PaymentRepo paymentRepo;

    @Override
    public Payment savePaymentTransaction(CreatePaymentRequest createPaymentRequest) {
        PaymentEntity paymentData = new PaymentEntity();
        paymentData.setOrderId(createPaymentRequest.getOrderId());
        paymentData.setTransactionId(createPaymentRequest.getTransactionId());
        paymentData.setMerchantId(createPaymentRequest.getMerchantId());
        paymentData.setGrossAmount(createPaymentRequest.getGrossAmount());
        paymentData.setCurrency(createPaymentRequest.getCurrency());
        paymentData.setTransactionTime(createPaymentRequest.getTransactionTime());
        paymentData.setTransactionStatus(createPaymentRequest.getTransactionStatus());
        paymentData.setExpiryTime(createPaymentRequest.getExpiryTime());
        paymentData.setFraudStatus(createPaymentRequest.getFraudStatus());
        paymentData.setPaymentType(createPaymentRequest.getPaymentType());
        paymentData.setPaymentMethod(createPaymentRequest.getPaymentMethod());
        paymentData.setPaymentVaNumbers(createPaymentRequest.getPaymentVaNumbers());
        paymentData.setTotalPrice(createPaymentRequest.getTotalPrice());
        paymentData.setTotalTax(createPaymentRequest.getTotalTax());
        paymentData.setTotalDiscount(createPaymentRequest.getTotalDiscount());
        paymentData.setUserId(createPaymentRequest.getUserId());

        PaymentEntity paymentEntity = paymentRepo.saveAndFlush(paymentData);
        return getPaymentDto(paymentEntity);
    }

    private static Payment getPaymentDto(PaymentEntity paymentEntity) {
        Payment payment = new Payment();
        payment.setPaymentId(paymentEntity.getPaymentId());
        payment.setUserId(paymentEntity.getUserId());
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
