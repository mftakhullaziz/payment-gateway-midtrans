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
        PaymentEntity savePayment = new PaymentEntity();
        PaymentEntity paymentEntity = paymentRepository.saveAndFlush(savePayment);
        return null;
    }
}
