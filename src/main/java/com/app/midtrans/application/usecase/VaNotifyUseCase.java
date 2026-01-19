package com.app.midtrans.application.usecase;

import com.app.midtrans.application.UseCaseExecutor;
import com.app.midtrans.domain.callback.Callback;
import com.app.midtrans.domain.callback.CallbackPersistencePort;
import com.app.midtrans.domain.callback.CallbackService;
import com.app.midtrans.domain.customer.Customer;
import com.app.midtrans.domain.customer.CustomerPersistencePort;
import com.app.midtrans.domain.customer.CustomerService;
import com.app.midtrans.domain.email.EmailGatewayPort;
import com.app.midtrans.domain.payment.PaymentPersistencePort;
import com.app.midtrans.infra.adapter.inbound.request.VaTransferNotifyRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class VaNotifyUseCase
    extends UseCaseExecutor<VaTransferNotifyRequest, Void> {

    private final ObjectMapper objectMapper;

    private final CallbackPersistencePort callbackPersistencePort;
    private final CallbackService callbackService;

    private final PaymentPersistencePort paymentPersistencePort;

    private final EmailGatewayPort emailGatewayPort;

    private final CustomerPersistencePort customerPersistencePort;
    private final CustomerService customerService;

    @Transactional(
        propagation = Propagation.REQUIRES_NEW,
        isolation = Isolation.READ_COMMITTED)
    @Override
    public void execute(VaTransferNotifyRequest input, Void output) {
        Callback callback = toCallback(input);
        callbackService.validate(callback);
        callbackPersistencePort.saveCallback(callback);

        if ("settlement".equalsIgnoreCase(input.getTransactionStatus())) {
            paymentPersistencePort.updatePayment(
                input.getTransactionStatus(),
                input.getOrderId(),
                input.getTransactionId());

            Long customerId = paymentPersistencePort.findCustomerId(
                input.getOrderId(),
                input.getTransactionId());
            customerService.customerIdMustBePresent(customerId);
            Customer customer = customerPersistencePort.getCustomerById(customerId);

            emailGatewayPort.sendStatusEmail(
                customer.getEmail(),
                customer.getName(),
                input.getOrderId(),
                input.getVaNumbers().getFirst().getVaNumber(),
                input.getVaNumbers().getFirst().getBank(),
                input.getSettlementTime(),
                input.getTransactionStatus(),
                input.getGrossAmount()
            );
        }
    }

    private Callback toCallback(VaTransferNotifyRequest input) {
        return Callback.builder()
            .orderId(input.getOrderId())
            .transactionId(input.getTransactionId())
            .dataCallbacks(objectMapper.valueToTree(input))
            .build();
    }

}
