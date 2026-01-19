package com.app.gatepay.application.usecase.v2;

import com.app.gatepay.api.v2.payment.dto.CreatePaymentRequest;
import com.app.gatepay.api.v2.payment.dto.CreatePaymentResponse;
import com.app.gatepay.api.v2.payment.dto.PaymentMethodType;
import com.app.gatepay.application.UseCaseExecutor;
import com.app.gatepay.domain.bank.Bank;
import com.app.gatepay.domain.bank.BankPersistencePort;
import com.app.gatepay.domain.bank.BankService;
import com.app.gatepay.domain.customer.Customer;
import com.app.gatepay.domain.customer.CustomerPersistencePort;
import com.app.gatepay.domain.customer.CustomerService;
import com.app.gatepay.domain.email.EmailGatewayPort;
import com.app.gatepay.domain.payment.Payment;
import com.app.gatepay.domain.payment.PaymentPersistencePort;
import com.app.gatepay.domain.payment.PaymentService;
import com.app.gatepay.infra.gateway.routing.PaymentGatewayRouter;
import com.app.gatepay.shared.enums.EWalletChannel;
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
public class CreatePaymentUseCase extends UseCaseExecutor<CreatePaymentRequest, CreatePaymentResponse> {

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
    public void execute(CreatePaymentRequest input, CreatePaymentResponse output) {
        if (input.getMethodType() == null) {
            throw new IllegalArgumentException("methodType is required");
        }

        // Check customer must be eligible to create payment
        Customer customer = customerPersistencePort.getCustomerById(input.getCustomerId());
        customerService.ensureEligibleForPayment(customer);

        Payment paymentRequest;
        Payment payment;
        VaChannel vaChannelForResponse = null;

        if (input.getMethodType() == PaymentMethodType.VA) {
            VaChannel vaChannel = VaChannel.from(input.getChannel());
            vaChannelForResponse = vaChannel;

            Bank bank = bankPersistencePort.getBankByName(vaChannel.getBankName());
            bankService.bankMustBeEligible(bank);

            paymentRequest = Payment.builder()
                .customerId(input.getCustomerId())
                .orderId(input.getOrderId())
                .totalAmount(input.getTotalAmount())
                .bankTransfer(bank.getName())
                .vaChannel(vaChannel)
                .build();

            payment = paymentGatewayRouter.gateway(input.getProvider()).executeTransferVA(paymentRequest);

            // Email reminder (VA only for now)
            emailGatewayPort.sendReminderEmail(
                customer.getEmail(),
                customer.getName(),
                payment.getVaNumber(),
                vaChannel.getBankName(),
                payment.getExpiredTime(),
                payment.getTransactionStatus()
            );
        } else if (input.getMethodType() == PaymentMethodType.EWALLET) {
            // Phase 1 e-wallet: API accepts the method, but provider adapter implementation is pending.
            // To avoid returning null and breaking persistence, we currently treat it as unsupported.
            throw new UnsupportedOperationException("EWALLET is not implemented yet for provider: " +
                (input.getProvider() != null ? input.getProvider() : "DEFAULT"));
        } else {
            throw new UnsupportedOperationException("Unsupported methodType: " + input.getMethodType());
        }

        paymentService.validateForSave(payment);
        paymentPersistencePort.savePayment(payment);

        // Build response
        if (vaChannelForResponse != null) {
            buildResponse(customer, output, payment, vaChannelForResponse);
        } else {
            output.setCustomerId(payment.getCustomerId());
            output.setOrderId(payment.getOrderId());
            output.setProvider(payment.getProvider());
            output.setTransactionId(payment.getTransactionId());
            output.setStatus(payment.getTransactionStatus());
            output.setPaymentType(payment.getPaymentType());
            output.setTransactionTime(formatTime(payment.getTransactionTime()));
            output.setExpiredTime(formatTime(payment.getExpiredTime()));
            output.setBank(null);
            output.setVirtualAccountNumber(null);
        }
    }

    private void buildResponse(
        Customer customer,
        CreatePaymentResponse output,
        Payment payment,
        VaChannel vaChannel
    ) {
        output.setCustomerId(payment.getCustomerId());
        output.setOrderId(payment.getOrderId());
        output.setProvider(payment.getProvider());

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

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime parsed = LocalDateTime.parse(time, inputFormatter);
        return parsed.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
