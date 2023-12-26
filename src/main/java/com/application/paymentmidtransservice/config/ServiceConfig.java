package com.application.paymentmidtransservice.config;

import com.application.paymentmidtransservice.core.repository.PaymentRepository;
import com.application.paymentmidtransservice.core.service.PaymentService;
import com.application.paymentmidtransservice.core.service.PaymentServiceImpl;
import com.application.paymentmidtransservice.deliver.usecase.PaymentUsecase;
import com.application.paymentmidtransservice.deliver.usecase.PaymentUsecaseImpl;
import com.application.paymentmidtransservice.middleware.MidtransGateway;
import com.application.paymentmidtransservice.middleware.MidtransGatewayImpl;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
//@EnableJpaRepositories
@EnableJpaRepositories(basePackages = "com.application.paymentmidtransservice.core.repository")
@EntityScan(basePackages = "com.application.paymentmidtransservice.core.entity") // Add this line
public class ServiceConfig {

    @Bean
    public MidtransGateway midtransGateway(PaymentConfig paymentConfig) {
        return new MidtransGatewayImpl(paymentConfig);
    }

    @Bean
    public PaymentService paymentService(PaymentRepository paymentRepository) {
        return new PaymentServiceImpl(paymentRepository);
    }

    @Bean
    public PaymentUsecase paymentUsecase(PaymentService paymentService, MidtransGateway midtransGateway) {
        return new PaymentUsecaseImpl(paymentService, midtransGateway);
    }

}
