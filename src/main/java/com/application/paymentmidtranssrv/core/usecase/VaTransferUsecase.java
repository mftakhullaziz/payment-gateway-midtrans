package com.application.paymentmidtranssrv.core.usecase;

import com.application.paymentmidtranssrv.app.annotation.Usecase;
import com.application.paymentmidtranssrv.app.exception.BusinessException;
import com.application.paymentmidtranssrv.core.gateway.CustomerGateway;
import com.application.paymentmidtranssrv.core.gateway.EmailGateway;
import com.application.paymentmidtranssrv.core.gateway.PaymentGateway;
import com.application.paymentmidtranssrv.domain.PaymentTypes;
import com.application.paymentmidtranssrv.domain.model.Payment;
import com.application.paymentmidtranssrv.domain.model.PaymentMidtrans;
import com.application.paymentmidtranssrv.domain.request.CreatePaymentRequest;
import com.application.paymentmidtranssrv.domain.request.PaymentRequest;
import com.application.paymentmidtranssrv.domain.response.PaymentResponse;
import com.application.paymentmidtranssrv.core.gateway.MidtransGateway;
import com.application.paymentmidtranssrv.domain.response.PaymentMidtransResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.Timestamp;

@Log4j2
@Usecase
@RequiredArgsConstructor
public class VaTransferUsecase {

    private final CustomerGateway customerGateway;
    private final PaymentGateway paymentGateway;
    private final MidtransGateway midtransGateway;
    private final EmailGateway emailGateway;
    private final PlatformTransactionManager transactionManager;

    public PaymentResponse vaTransferPayment(PaymentRequest paymentRequest) {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setName("create-payment-transaction-by-user-id: " + paymentRequest.getUsersInfo().getCustomerId());
        TransactionStatus transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);

        try {
            PaymentMidtrans paymentMidtrans = paymentDtoMapper(paymentRequest);
            if (paymentMidtrans == null) {
                throw new BusinessException("Invalid payment-midtrans payload", HttpStatus.UNPROCESSABLE_ENTITY.value()); // 422
            }

            Boolean customerExist = customerGateway.checkCustomerAndHasRole(paymentRequest.getUsersInfo().getEmail(), "CUSTOMER");
            if (Boolean.FALSE.equals(customerExist)) {
                throw new BusinessException("Invalid customer for payment", HttpStatus.NOT_FOUND.value());
            }

            PaymentTypes paymentTypes = paymentMidtrans.getPaymentTypes();
            PaymentMidtransResponse midtransResponse = createPaymentTransaction(paymentMidtrans, paymentTypes);
            if (midtransResponse == null) {
                throw new BusinessException("Failed to create payment transaction with Midtrans", HttpStatus.BAD_GATEWAY.value()); // 502
            }

            CreatePaymentRequest createPaymentRequest = createPaymentRequests(paymentRequest, midtransResponse);
            Payment payment = paymentGateway.savePaymentTransaction(createPaymentRequest);
            if (payment == null) {
                throw new BusinessException("Failed to persist payment transaction to database", HttpStatus.INTERNAL_SERVER_ERROR.value()); // 500
            }

            emailGateway.publishEmailRemainderNotification(
                paymentRequest.getUsersInfo().getEmail(),
                paymentRequest.getUsersInfo().getName(),
                midtransResponse.getVaNumbers().getFirst().getVa_number(),
                midtransResponse.getVaNumbers().getFirst().getBank(),
                midtransResponse.getExpiryTime(),
                midtransResponse.getTransactionStatus().toUpperCase());

            transactionManager.commit(transactionStatus);
            return getPayments(payment);
        } catch (BusinessException be) {
            transactionManager.rollback(transactionStatus);
            throw be;
        } catch (Exception e) {
            transactionManager.rollback(transactionStatus);
            throw new BusinessException("Unexpected server error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    private PaymentMidtransResponse createPaymentTransaction(
        PaymentMidtrans paymentMidtrans, PaymentTypes paymentTypes)
    {
        return switch (paymentTypes) {
            case BANK_TRANSFER -> midtransGateway.executePayMidtransBankTransfer(paymentMidtrans);
            case CREDIT_CARD -> midtransGateway.executePayMidtransCreditCard(paymentMidtrans);
            default -> throw new BusinessException("enum not found", HttpStatus.UNPROCESSABLE_ENTITY.value());
        };
    }

    private static CreatePaymentRequest createPaymentRequests(
        PaymentRequest paymentRequest,
        PaymentMidtransResponse midtransResponse
    ) {
        return CreatePaymentRequest.builder()
            .transactionId(midtransResponse.getTransactionId())
            .transactionTime(Timestamp.valueOf(midtransResponse.getTransactionTime()))
            .transactionStatus(midtransResponse.getTransactionStatus())
            .orderId(midtransResponse.getOrderId())
            .merchantId(midtransResponse.getMerchantId())
            .grossAmount(Double.valueOf(midtransResponse.getGrossAmount()))
            .currency(midtransResponse.getCurrency())
            .expiryTime(Timestamp.valueOf(midtransResponse.getExpiryTime()))
            .fraudStatus(midtransResponse.getFraudStatus())
            .paymentType(midtransResponse.getPaymentType())
            .paymentMethod(midtransResponse.getVaNumbers().getFirst().getBank())
            .paymentVaNumbers(midtransResponse.getVaNumbers().getFirst().getVa_number())
            .totalPrice(paymentRequest.getTotalPrice())
            .totalTax(paymentRequest.getTotalTax())
            .totalDiscount(paymentRequest.getTotalDiscount())
            .customerId(paymentRequest.getUsersInfo().getCustomerId())
            .build();
    }

    private static PaymentResponse getPayments(Payment payment) {
        return PaymentResponse.builder()
            .paymentId(payment.getPaymentId())
            .customerId(payment.getCustomerId())
            .orderId(payment.getOrderId())
            .transactionId(payment.getTransactionId())
            .merchantId(payment.getMerchantId())
            .grossAmount(payment.getGrossAmount())
            .currency(payment.getCurrency())
            .transactionTime(payment.getTransactionTime())
            .transactionStatus(payment.getTransactionStatus())
            .expiryTime(payment.getExpiryTime())
            .fraudStatus(payment.getFraudStatus())
            .paymentType(payment.getPaymentType())
            .paymentMethod(payment.getPaymentMethod())
            .paymentVaNumbers(payment.getPaymentVaNumbers())
            .totalPrice(payment.getTotalPrice())
            .totalTax(payment.getTotalTax())
            .totalDiscount(payment.getTotalDiscount())
            .build();
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
