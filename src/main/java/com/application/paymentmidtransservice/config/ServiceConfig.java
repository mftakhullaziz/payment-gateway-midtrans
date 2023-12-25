package com.application.paymentmidtransservice.config;

import com.application.paymentmidtransservice.middleware.MidtransGateway;
import com.application.paymentmidtransservice.middleware.MidtransGatewayImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public MidtransGateway midtransGateway(PaymentConfig paymentConfig) {
        return new MidtransGatewayImpl(paymentConfig);
    }

}
