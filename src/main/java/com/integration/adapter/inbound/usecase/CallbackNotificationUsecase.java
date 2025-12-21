package com.integration.adapter.inbound.usecase;

import com.app.midtrans.shared.annotation.Usecase;
import com.app.midtrans.shared.exception.BusinessException;
import com.integration.adapter.ports.outbound.mysql.jpa.CustomerJPAOutboundPort;
import com.integration.adapter.ports.outbound.email.EmailOutboundPort;
import com.integration.adapter.ports.outbound.mysql.jpa.PaymentCallbackJPAOutboundPort;
import com.integration.adapter.ports.outbound.mysql.jpa.PaymentJPAOutboundPort;
import com.app.midtrans.shared.dto.coreapis.Customer;
import com.app.midtrans.shared.dto.notifications.PaymentCallback;
import com.app.midtrans.infra.adapter.inbound.request.VaTransferNotifyRequest;
import com.app.midtrans.shared.utils.JsonUtils;
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
    public void handleCallbackNotify(VaTransferNotifyRequest request) {
        try {
            log.info("Received transaction status: {}", request.getTransactionStatus());
            PaymentCallback constructPaymentCallback = PaymentCallback.builder()
                .transactionId(request.getTransactionId())
                .orderId(request.getOrderId())
                .callbacks(request)
                .build();
            log.info("Constructed payment callback: {}", JsonUtils.toJson(constructPaymentCallback));

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
