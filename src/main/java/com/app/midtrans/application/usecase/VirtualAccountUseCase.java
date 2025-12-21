package com.app.midtrans.application.usecase;

import com.app.midtrans.application.UseCaseExecutor;
import com.app.midtrans.domain.bank.Bank;
import com.app.midtrans.domain.bank.BankPersistencePort;
import com.app.midtrans.domain.bank.BankService;
import com.app.midtrans.domain.bankAccount.BankAccountPersistencePort;
import com.app.midtrans.domain.bankAccount.BankAccountService;
import com.app.midtrans.domain.customer.Customer;
import com.app.midtrans.domain.customer.CustomerPersistencePort;
import com.app.midtrans.domain.customer.CustomerService;
import com.app.midtrans.domain.payment.PaymentGatewayPort;
import com.app.midtrans.domain.payment.PaymentPersistencePort;
import com.app.midtrans.domain.payment.PaymentService;
import com.app.midtrans.infra.adapter.inbound.request.VirtualAccountRequest;
import com.app.midtrans.infra.adapter.inbound.response.VirtualAccountResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Component
@RequiredArgsConstructor
public class VirtualAccountUseCase
    extends UseCaseExecutor<VirtualAccountRequest, VirtualAccountResponse> {

    private final BankAccountPersistencePort bankAccountPersistencePort;
    private final BankAccountService bankAccountService;

    private final BankPersistencePort bankPersistencePort;
    private final BankService bankService;

    private final CustomerPersistencePort customerPersistencePort;
    private final CustomerService customerService;

    private final PaymentGatewayPort paymentGatewayPort;
    private final PaymentPersistencePort paymentPersistencePort;
    private final PaymentService paymentService;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public void execute(VirtualAccountRequest input, VirtualAccountResponse output) {
        // Check customer must be eligible to create VA
        Customer customer = customerPersistencePort.getCustomerById(input.getCustomerId());
        customerService.ensureEligibleForPayment(customer);

        // If the customer is eligible, check bank account support create va
        String bankName = bankService.getBankName(input.getBankTransfer());
        Bank bank = bankPersistencePort.getBankByName(bankName);
        bankService.bankMustBeEligible(bank);

        // if a bank still eligible, now create va payment to midtrans
    }

}
