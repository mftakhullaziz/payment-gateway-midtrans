package com.app.gatepay.application.usecase;

import com.app.gatepay.application.UseCaseExecutor;
import com.app.gatepay.domain.customer.Customer;
import com.app.gatepay.domain.customer.CustomerPersistencePort;
import com.app.gatepay.domain.customer.CustomerService;
import com.app.gatepay.domain.payment.PaymentGatewayPort;
import com.app.gatepay.domain.payment.PaymentService;
import com.app.gatepay.infra.adapter.inbound.request.EWalletTransferRequest;
import com.app.gatepay.infra.adapter.inbound.response.EWalletTransferResponse;
import com.app.gatepay.shared.enums.EWalletChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EWalletPaymentUseCase
    extends UseCaseExecutor<EWalletTransferRequest, EWalletTransferResponse> {

    private final PaymentGatewayPort paymentGatewayPort;
    private final PaymentService paymentService;

    private final CustomerPersistencePort customerPersistencePort;
    private final CustomerService customerService;

    @Override
    public void execute(EWalletTransferRequest input, EWalletTransferResponse output) {
        Customer customer = customerPersistencePort.getCustomerById(input.getCustomerId());
        customerService.ensureEligibleForPayment(customer);

        EWalletChannel channel = EWalletChannel.fromPaymentType(input.getPaymentType());


    }

    private void buildResponse(EWalletTransferResponse output) {

    }

}
