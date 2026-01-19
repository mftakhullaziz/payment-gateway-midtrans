package com.app.midtrans.application.usecase;

import com.app.midtrans.application.UseCaseExecutor;
import com.app.midtrans.domain.customer.Customer;
import com.app.midtrans.domain.customer.CustomerPersistencePort;
import com.app.midtrans.domain.customer.CustomerService;
import com.app.midtrans.domain.payment.PaymentGatewayPort;
import com.app.midtrans.domain.payment.PaymentService;
import com.app.midtrans.infra.adapter.inbound.request.EWalletTransferRequest;
import com.app.midtrans.infra.adapter.inbound.response.EWalletTransferResponse;
import com.app.midtrans.shared.enums.EWalletChannel;
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
