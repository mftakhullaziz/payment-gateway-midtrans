package com.application.paymentmidtransservice.core.usecase;

import com.application.paymentmidtransservice.core.service.PaymentService;
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
        PaymentMidtransResponse midtransResponse = midtransGateway.executePaymentMidtrans(paymentMidtransDto);

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

        PaymentDto paymentDto = paymentService.savePaymentTransaction(createPaymentRequest);

        return null;
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
