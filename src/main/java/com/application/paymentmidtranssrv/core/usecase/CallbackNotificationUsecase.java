package com.application.paymentmidtranssrv.core.usecase;

import com.application.paymentmidtranssrv.app.annotation.Usecase;
import com.application.paymentmidtranssrv.app.exception.BusinessException;
import com.application.paymentmidtranssrv.core.gateway.EmailGateway;
import com.application.paymentmidtranssrv.core.gateway.PaymentCallbackGateway;
import com.application.paymentmidtranssrv.core.gateway.PaymentGateway;
import com.application.paymentmidtranssrv.domain.model.Payment;
import com.application.paymentmidtranssrv.domain.model.PaymentCallback;
import com.application.paymentmidtranssrv.domain.request.VaTransferCallbackRequest;
import com.application.paymentmidtranssrv.utility.JsonUtility;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Usecase
@RequiredArgsConstructor
public class CallbackNotificationUsecase {

    private final PaymentCallbackGateway paymentCallbackGateway;
    private final PaymentGateway paymentGateway;
    private final JsonUtility jsonUtility;
    private final EmailGateway emailGateway;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = BusinessException.class)
    public void handleCallbackNotify(VaTransferCallbackRequest request) {
        try {
            log.info("Received transaction status: {}", request.getTransactionStatus());
            PaymentCallback constructPaymentCallback = PaymentCallback.builder()
                .transactionId(request.getTransactionId())
                .orderId(request.getOrderId())
                .callbacks(request)
                .build();
            log.info("Constructed payment callback: {}", JsonUtility.toJson(constructPaymentCallback));

            paymentCallbackGateway.writeCallbackOnDB(constructPaymentCallback);
            log.info("Payment callback successfully write to db");

            if ("settlement".equalsIgnoreCase(request.getTransactionStatus())) {
                paymentGateway.updatePayment(
                    request.getTransactionStatus(),
                    request.getOrderId(),
                    request.getTransactionId());
            }

            // When Success payment status send email
        } catch (BusinessException e) {
            throw new BusinessException(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        }
    }
}
