package com.application.paymentmidtransservice.app.config;

import com.application.paymentmidtransservice.app.property.EmailProperty;
import com.application.paymentmidtransservice.app.property.PaymentProperty;
import com.application.paymentmidtransservice.infra.mysql.repository.PaymentRepository;
import com.application.paymentmidtransservice.core.gateway.EmailGateway;
import com.application.paymentmidtransservice.core.gateway.PaymentGateway;
import com.application.paymentmidtransservice.core.gateway.impl.EmailService;
import com.application.paymentmidtransservice.core.gateway.impl.PaymentService;
import com.application.paymentmidtransservice.core.usecase.PaymentUsecase;
import com.application.paymentmidtransservice.core.usecase.PaymentUsecaseImpl;
import com.application.paymentmidtransservice.core.gateway.MidtransGateway;
import com.application.paymentmidtransservice.core.gateway.impl.MidtransService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ServiceConfig {

    @Bean
    public MidtransGateway midtransGateway(PaymentProperty paymentProperty) {
        return new MidtransService(paymentProperty);
    }

    @Bean
    public PaymentGateway paymentGateway(PaymentRepository paymentRepository) {
        return new PaymentService(paymentRepository);
    }

    @Bean
    public EmailGateway emailGateway(EmailProperty emailProperty) {
        return new EmailService(emailProperty);
    }

    @Bean
    public PaymentUsecase paymentUsecase(PaymentGateway paymentGateway,
                                         MidtransGateway midtransGateway,
                                         EmailGateway emailGateway,
                                         PlatformTransactionManager platformTransactionManager) {
        return new PaymentUsecaseImpl(
            paymentGateway, midtransGateway, emailGateway, platformTransactionManager);
    }

}
