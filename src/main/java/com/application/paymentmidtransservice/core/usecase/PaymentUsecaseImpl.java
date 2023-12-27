package com.application.paymentmidtransservice.core.usecase;

import com.application.paymentmidtransservice.core.service.PaymentService;
import com.application.paymentmidtransservice.domain.PaymentTypes;
import com.application.paymentmidtransservice.domain.dto.PaymentDto;
import com.application.paymentmidtransservice.domain.dto.PaymentMidtransDto;
import com.application.paymentmidtransservice.domain.request.CreatePaymentRequest;
import com.application.paymentmidtransservice.domain.request.PaymentRequest;
import com.application.paymentmidtransservice.domain.response.PaymentResponse;
import com.application.paymentmidtransservice.middleware.MidtransGateway;
import com.application.paymentmidtransservice.middleware.PaymentMidtransResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.sql.Timestamp;

@Log4j2
@RequiredArgsConstructor
public class PaymentUsecaseImpl implements PaymentUsecase {

    private final PaymentService paymentService;
    private final MidtransGateway midtransGateway;

    @Transactional
    @Override
    public PaymentResponse executePaymentTransaction(PaymentRequest paymentRequest) {
        PaymentMidtransDto paymentMidtransDto = paymentDtoMapper(paymentRequest);

        PaymentTypes paymentTypes = paymentMidtransDto.getPaymentTypes();
        PaymentMidtransResponse midtransResponse;
        switch (paymentTypes) {
            case BANK_TRANSFER -> midtransResponse = midtransGateway.executePayMidtransBankTransfer(paymentMidtransDto);
            case CREDIT_CARD -> midtransResponse = midtransGateway.executePayMidtransCreditCard(paymentMidtransDto);
            default -> throw new RuntimeException("not found");
        }

        CreatePaymentRequest createPaymentRequest = createPaymentRequests(paymentRequest, midtransResponse);
        PaymentDto paymentDto = paymentService.savePaymentTransaction(createPaymentRequest);
        return getPayments(paymentDto);
    }

    private static CreatePaymentRequest createPaymentRequests(PaymentRequest paymentRequest, PaymentMidtransResponse midtransResponse) {
        CreatePaymentRequest createPaymentRequest = new CreatePaymentRequest();
        createPaymentRequest.setTransactionId(midtransResponse.getTransactionId());
        createPaymentRequest.setTransactionTime(Timestamp.valueOf(midtransResponse.getTransactionTime()));
        createPaymentRequest.setTransactionStatus(midtransResponse.getTransactionStatus());
        createPaymentRequest.setOrderId(midtransResponse.getOrderId());
        createPaymentRequest.setMerchantId(midtransResponse.getMerchantId());
        createPaymentRequest.setGrossAmount(Double.valueOf(midtransResponse.getGrossAmount()));
        createPaymentRequest.setCurrency(midtransResponse.getCurrency());
        createPaymentRequest.setExpiryTime(Timestamp.valueOf(midtransResponse.getExpiryTime()));
        createPaymentRequest.setFraudStatus(midtransResponse.getFraudStatus());
        createPaymentRequest.setPaymentType(midtransResponse.getPaymentType());
        createPaymentRequest.setPaymentMethod(midtransResponse.getVaNumbers().getFirst().getBank());
        createPaymentRequest.setPaymentVaNumbers(midtransResponse.getVaNumbers().getFirst().getVa_number());
        createPaymentRequest.setTotalPrice(paymentRequest.getTotalPrice());
        createPaymentRequest.setTotalTax(paymentRequest.getTotalTax());
        createPaymentRequest.setTotalDiscount(paymentRequest.getTotalDiscount());
        createPaymentRequest.setUserId(paymentRequest.getUsersInfo().getUserId());
        return createPaymentRequest;
    }

    private static PaymentResponse getPayments(PaymentDto paymentEntity) {
        PaymentResponse paymentDto = new PaymentResponse();
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

    private PaymentMidtransDto paymentDtoMapper(PaymentRequest request) {
        return PaymentMidtransDto.builder()
            .paymentTypes(request.getPaymentType())
            .bankType(request.getBankType())
            .orderId(request.getOrderId())
            .totalPrice(request.getTotalPrice())
            .build();
    }

}
