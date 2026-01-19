package com.app.gatepay.application.usecase;

import com.app.gatepay.application.UseCaseExecutor;
import com.app.gatepay.domain.bank.Bank;
import com.app.gatepay.domain.bank.BankPersistencePort;
import com.app.gatepay.domain.bank.BankService;
import com.app.gatepay.domain.customer.Customer;
import com.app.gatepay.domain.customer.CustomerPersistencePort;
import com.app.gatepay.domain.customer.CustomerService;
import com.app.gatepay.domain.email.EmailGatewayPort;
import com.app.gatepay.domain.payment.Payment;
import com.app.gatepay.infra.gateway.routing.PaymentGatewayRouter;
import com.app.gatepay.domain.payment.PaymentPersistencePort;
import com.app.gatepay.domain.payment.PaymentService;
import com.app.gatepay.infra.adapter.inbound.request.VaTransferRequest;
import com.app.gatepay.infra.adapter.inbound.response.VaTransferResponse;
import com.app.gatepay.shared.enums.VaChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Log4j2
@Component
@RequiredArgsConstructor
public class VaPaymentUseCase
    extends UseCaseExecutor<VaTransferRequest, VaTransferResponse> {

    private final BankPersistencePort bankPersistencePort;
    private final BankService bankService;

    private final CustomerPersistencePort customerPersistencePort;
    private final CustomerService customerService;

    private final PaymentGatewayRouter paymentGatewayRouter;
    private final PaymentPersistencePort paymentPersistencePort;
    private final PaymentService paymentService;

    private final EmailGatewayPort emailGatewayPort;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public void execute(VaTransferRequest input, VaTransferResponse output) {
        // Check customer must be eligible to create VA
        Customer customer = customerPersistencePort.getCustomerById(input.getCustomerId());
        customerService.ensureEligibleForPayment(customer);

        // 2. Resolve VA Channel
        VaChannel vaChannel = VaChannel.from(input.getBankTransfer());

        // 3. Validate bank eligibility
        Bank bank = bankPersistencePort.getBankByName(vaChannel.getBankName());
        bankService.bankMustBeEligible(bank);

        // if a bank still eligible, now create va payment to gateway
        Payment paymentRequest = toPaymentRequest(input, bank, vaChannel);
        Payment payment = paymentGatewayRouter.defaultGateway().executeTransferVA(paymentRequest);
        paymentService.validateForSave(payment);
        paymentPersistencePort.savePayment(payment);

        // Send email notification to a customer
        emailGatewayPort.sendReminderEmail(
            customer.getEmail(),
            customer.getName(),
            payment.getVaNumber(),
            vaChannel.getBankName(),
            payment.getExpiredTime(),
            payment.getTransactionStatus()
        );

        // Construct to virtual account response
        buildResponse(customer, output, payment, vaChannel);
    }

    private Payment toPaymentRequest(
        VaTransferRequest input,
        Bank bank,
        VaChannel vaChannel
    ) {
        return Payment.builder()
            .customerId(input.getCustomerId())
            .orderId(input.getOrderId())
            .totalAmount(input.getTotalAmount())
            .bankTransfer(bank.getName())
            .vaChannel(vaChannel)
            .build();
    }

    private void buildResponse(
        Customer customer,
        VaTransferResponse output,
        Payment payment,
        VaChannel vaChannel
    ) {
        output.setCustomerId(payment.getCustomerId());
        output.setEmail(customer.getEmail());

        output.setOrderId(payment.getOrderId());
        output.setTransactionId(payment.getTransactionId());
        output.setStatus(payment.getTransactionStatus());
        output.setPaymentType(payment.getPaymentType());

        output.setBank(vaChannel.getBankName());
        output.setVirtualAccountNumber(resolveVaNumber(payment));

        output.setTransactionTime(formatTime(payment.getTransactionTime()));
        output.setExpiredTime(formatTime(payment.getExpiredTime()));
    }

    private String resolveVaNumber(Payment payment) {
        if (payment.isMandiri()) {
            return payment.getBillKey();
        }
        return payment.getVaNumber();
    }

    private String formatTime(String time) {
        if (time == null || time.isBlank()) return null;

        DateTimeFormatter inputFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime parsed = LocalDateTime.parse(time, inputFormatter);

        return parsed.format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        );
    }

}
