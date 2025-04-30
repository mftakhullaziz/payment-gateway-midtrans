package com.integration.paymentmidtrans.domain.coreapis.usecase;

import com.integration.paymentmidtrans.shared.annotation.Usecase;
import com.integration.paymentmidtrans.shared.exception.BusinessException;
import com.integration.paymentmidtrans.port.inbound.jpagateway.CustomerGateway;
import com.integration.paymentmidtrans.port.inbound.jpagateway.EmailGateway;
import com.integration.paymentmidtrans.port.inbound.jpagateway.PaymentGateway;
import com.integration.paymentmidtrans.domain.coreapis.mapper.VaTransferUsecaseTransformer;
import com.integration.paymentmidtrans.shared.enums.PaymentTypes;
import com.integration.paymentmidtrans.domain.coreapis.dto.Payment;
import com.integration.paymentmidtrans.domain.coreapis.dto.VaTransferMidtrans;
import com.integration.paymentmidtrans.domain.coreapis.request.CreatePaymentRequest;
import com.integration.paymentmidtrans.domain.coreapis.request.PaymentRequest;
import com.integration.paymentmidtrans.domain.coreapis.response.PaymentResponse;
import com.integration.paymentmidtrans.port.inbound.midtransgateway.MidtransGateway;
import com.integration.paymentmidtrans.domain.coreapis.response.PaymentMidtransResponse;
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
        defaultTransactionDefinition.setName("create-payment-transaction-by-user-id: " + paymentRequest.getCustomerInfo().getCustomerId());
        TransactionStatus transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);

        try {
            VaTransferMidtrans vaTransferMidtrans = VaTransferUsecaseTransformer.transformToVATransferMidtrans(paymentRequest);
            if (vaTransferMidtrans == null) {
                throw new BusinessException("Invalid payment-midtrans payload", HttpStatus.UNPROCESSABLE_ENTITY.value()); // 422
            }

            Boolean customerExist = customerGateway.checkCustomerAndHasRole(paymentRequest.getCustomerInfo().getEmail(), "CUSTOMER");
            if (Boolean.FALSE.equals(customerExist)) {
                throw new BusinessException("Invalid customer for payment", HttpStatus.NOT_FOUND.value());
            }

            PaymentTypes paymentTypes = vaTransferMidtrans.getPaymentTypes();
            PaymentMidtransResponse midtransResponse = createPaymentTransaction(vaTransferMidtrans, paymentTypes);
            if (midtransResponse == null) {
                throw new BusinessException("Failed to create payment transaction with Midtrans", HttpStatus.BAD_GATEWAY.value()); // 502
            }

            CreatePaymentRequest createPaymentRequest = createPaymentRequests(paymentRequest, midtransResponse);
            Payment payment = paymentGateway.savePaymentTransaction(createPaymentRequest);
            if (payment == null) {
                throw new BusinessException("Failed to persist payment transaction to database", HttpStatus.INTERNAL_SERVER_ERROR.value()); // 500
            }

            emailGateway.publishEmailRemainderNotification(
                paymentRequest.getCustomerInfo().getEmail(),
                paymentRequest.getCustomerInfo().getFirstname() + " " + paymentRequest.getCustomerInfo().getLastname(),
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
        VaTransferMidtrans vaTransferMidtrans, PaymentTypes paymentTypes)
    {
        return switch (paymentTypes) {
            case BANK_TRANSFER -> midtransGateway.executePayMidtransBankTransfer(vaTransferMidtrans);
            case CREDIT_CARD -> midtransGateway.executePayMidtransCreditCard(vaTransferMidtrans);
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
            .customerId(paymentRequest.getCustomerInfo().getCustomerId())
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
            .paymentType(payment.getPaymentType())
            .paymentMethod(payment.getPaymentMethod())
            .virtualAccount(payment.getPaymentVaNumbers())
            .totalPrice(payment.getTotalPrice())
            .totalTax(payment.getTotalTax())
            .totalDiscount(payment.getTotalDiscount())
            .build();
    }

}
