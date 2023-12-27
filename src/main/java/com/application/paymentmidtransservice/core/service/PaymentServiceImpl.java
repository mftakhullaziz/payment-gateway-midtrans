package com.application.paymentmidtransservice.core.service;

import com.application.paymentmidtransservice.core.entity.PaymentEntity;
import com.application.paymentmidtransservice.core.repository.PaymentRepository;
import com.application.paymentmidtransservice.domain.dto.PaymentDto;
import com.application.paymentmidtransservice.domain.request.CreatePaymentRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public PaymentDto savePaymentTransaction(CreatePaymentRequest createPaymentRequest) {
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

        PaymentEntity paymentEntity = paymentRepository.saveAndFlush(paymentData);
        return getPaymentDto(paymentEntity);
    }

    private static PaymentDto getPaymentDto(PaymentEntity paymentEntity) {
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setPaymentId(paymentEntity.getPaymentId());
        paymentDto.setUserId(paymentEntity.getUserId());
        paymentDto.setOrderId(paymentEntity.getOrderId());
        paymentDto.setTransactionId(paymentEntity.getTransactionId());
        paymentDto.setMerchantId(paymentEntity.getMerchantId());
        paymentDto.setGrossAmount(paymentEntity.getGrossAmount());
        paymentDto.setCurrency(paymentEntity.getCurrency());
        paymentDto.setTransactionTime(paymentEntity.getTransactionTime());
        paymentDto.setTransactionStatus(paymentEntity.getTransactionStatus());
        paymentDto.setExpiryTime(paymentEntity.getExpiryTime());
        paymentDto.setFraudStatus(paymentEntity.getFraudStatus());
        paymentDto.setPaymentType(paymentEntity.getPaymentType());
        paymentDto.setPaymentMethod(paymentEntity.getPaymentMethod());
        paymentDto.setPaymentVaNumbers(paymentEntity.getPaymentVaNumbers());
        paymentDto.setTotalPrice(paymentEntity.getTotalPrice());
        paymentDto.setTotalTax(paymentEntity.getTotalTax());
        paymentDto.setTotalDiscount(paymentEntity.getTotalDiscount());
        return paymentDto;
    }
}
