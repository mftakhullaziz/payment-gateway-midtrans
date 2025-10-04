package com.integration.paymentmidtrans.adapter.inbound.usecase;

import com.integration.paymentmidtrans.shared.annotation.Usecase;
import com.integration.paymentmidtrans.shared.exception.BusinessException;
import com.integration.paymentmidtrans.ports.outbound.mysql.jpa.CustomerJPAOutboundPort;
import com.integration.paymentmidtrans.ports.outbound.email.EmailOutboundPort;
import com.integration.paymentmidtrans.ports.outbound.mysql.jpa.PaymentCallbackJPAOutboundPort;
import com.integration.paymentmidtrans.ports.outbound.mysql.jpa.PaymentJPAOutboundPort;
import com.integration.paymentmidtrans.shared.dto.coreapis.Customer;
import com.integration.paymentmidtrans.shared.dto.notifications.PaymentCallback;
import com.integration.paymentmidtrans.adapter.inbound.delivery.notifications.request.VaTransferCallbackRequest;
import com.integration.paymentmidtrans.shared.utility.JsonUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Usecase
@RequiredArgsConstructor
public class CallbackNotificationUsecase {

    private final PaymentCallbackJPAOutboundPort paymentCallbackJPAOutboundPort;
    private final PaymentJPAOutboundPort paymentJPAOutboundPort;
    private final EmailOutboundPort emailOutboundPort;
    private final CustomerJPAOutboundPort customerJPAOutboundPort;

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

            paymentCallbackJPAOutboundPort.writeCallbackOnDB(constructPaymentCallback);
            log.info("Payment callback successfully write to db");

            if ("settlement".equalsIgnoreCase(request.getTransactionStatus())) {
                paymentJPAOutboundPort.updatePayment(
                    request.getTransactionStatus(),
                    request.getOrderId(),
                    request.getTransactionId());

                Long customerId = paymentJPAOutboundPort.findCustomerIdByOrderIdAndTransactionId(request.getOrderId(), request.getTransactionId());
                Customer customer = customerJPAOutboundPort.getCustomerById(customerId);

                // When Success payment status send email
                emailOutboundPort.publishEmailStatusNotification(
                    customer.getEmail(),
                    customer.getName(),
                    request.getOrderId(),
                    request.getVaNumbers().getFirst().getVaNumber(),
                    request.getVaNumbers().getFirst().getBank(),
                    request.getSettlementTime(),
                    request.getTransactionStatus(),
                    request.getGrossAmount());
            }
        } catch (BusinessException e) {
            throw new BusinessException(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        }
    }
}
