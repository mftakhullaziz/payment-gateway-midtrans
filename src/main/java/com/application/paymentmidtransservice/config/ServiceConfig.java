package com.application.paymentmidtransservice.config;

import com.application.paymentmidtransservice.infra.mysql.repository.PaymentRepository;
import com.application.paymentmidtransservice.core.service.EmailGateway;
import com.application.paymentmidtransservice.core.service.PaymentGateway;
import com.application.paymentmidtransservice.core.service.impl.EmailService;
import com.application.paymentmidtransservice.core.service.impl.PaymentService;
import com.application.paymentmidtransservice.core.usecase.PaymentUsecase;
import com.application.paymentmidtransservice.core.usecase.PaymentUsecaseImpl;
import com.application.paymentmidtransservice.core.service.MidtransGateway;
import com.application.paymentmidtransservice.core.service.impl.MidtransService;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(basePackages = "com.application.paymentmidtransservice.infra.repository")
@EntityScan(basePackages = "com.application.paymentmidtransservice.persistence.entity") // Add this line
public class ServiceConfig {

    @Bean
    public MidtransGateway midtransGateway(PaymentConfig paymentConfig) {
        return new MidtransService(paymentConfig);
    }

    @Bean
    public PaymentGateway paymentGateway(PaymentRepository paymentRepository) {
        return new PaymentService(paymentRepository);
    }

    @Bean
    public EmailGateway emailGateway(EmailConfig emailConfig) {
        return new EmailService(emailConfig);
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
