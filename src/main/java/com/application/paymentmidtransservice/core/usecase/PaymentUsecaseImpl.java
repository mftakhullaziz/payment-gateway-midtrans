package com.application.paymentmidtransservice.core.usecase;

import com.application.paymentmidtransservice.core.gateway.EmailGateway;
import com.application.paymentmidtransservice.core.gateway.PaymentGateway;
import com.application.paymentmidtransservice.domain.PaymentTypes;
import com.application.paymentmidtransservice.domain.model.Payment;
import com.application.paymentmidtransservice.domain.model.PaymentMidtrans;
import com.application.paymentmidtransservice.domain.request.CreatePaymentRequest;
import com.application.paymentmidtransservice.domain.request.PaymentRequest;
import com.application.paymentmidtransservice.domain.response.PaymentResponse;
import com.application.paymentmidtransservice.core.gateway.MidtransGateway;
import com.application.paymentmidtransservice.domain.response.PaymentMidtransResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.Timestamp;

@Log4j2
@RequiredArgsConstructor
public class PaymentUsecaseImpl implements PaymentUsecase {

    private final PaymentGateway paymentGateway;
    private final MidtransGateway midtransGateway;
    private final EmailGateway emailGateway;
    private final PlatformTransactionManager transactionManager;

    @Override
    public PaymentResponse executePaymentTransaction(PaymentRequest paymentRequest) {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setName("create-payment-transaction-by-user-id: " + paymentRequest.getUsersInfo().getUserId());
        TransactionStatus transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);

        try {
            PaymentMidtrans paymentMidtrans = paymentDtoMapper(paymentRequest);

//            Boolean userValidation = userService.validationUserByUserIdAndEmail(paymentRequest.getUserId(), paymentRequest.getEmail());
//            if (userValidation != true) {
//                throw new ApiResponseException(HttpStatus.BadRequest, APIError.builder.errorMessage("your parameter user-id and email not valid"));
//            }

            PaymentTypes paymentTypes = paymentMidtrans.getPaymentTypes();
            PaymentMidtransResponse midtransResponse = createPaymentTransaction(paymentMidtrans, paymentTypes);

            CreatePaymentRequest createPaymentRequest = createPaymentRequests(paymentRequest, midtransResponse);
            Payment payment = paymentGateway.savePaymentTransaction(createPaymentRequest);

            emailGateway.publishEmailNotification(paymentRequest.getUsersInfo().getEmail(), "");

            // Commit transaction
            transactionManager.commit(transactionStatus);

            return getPayments(payment);
        } catch (Exception e) {
            transactionManager.rollback(transactionStatus);
            throw e;
        }
    }

    private PaymentMidtransResponse createPaymentTransaction(
        PaymentMidtrans paymentMidtrans, PaymentTypes paymentTypes)
    {
        return switch (paymentTypes) {
            case BANK_TRANSFER -> midtransGateway.executePayMidtransBankTransfer(paymentMidtrans);
            case CREDIT_CARD -> midtransGateway.executePayMidtransCreditCard(paymentMidtrans);
            default -> throw new RuntimeException("not found");
        };
    }

    private static CreatePaymentRequest createPaymentRequests(
        PaymentRequest paymentRequest, PaymentMidtransResponse midtransResponse)
    {
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

    private static PaymentResponse getPayments(Payment paymentEntity) {
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

    private PaymentMidtrans paymentDtoMapper(PaymentRequest request) {
        return PaymentMidtrans.builder()
            .paymentTypes(request.getPaymentType())
            .bankType(request.getBankType())
            .orderId(request.getOrderId())
            .totalPrice(request.getTotalPrice())
            .build();
    }

}
