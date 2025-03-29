package com.application.paymentmidtransservice.app.config;

import com.application.paymentmidtransservice.app.property.EmailProperty;
import com.application.paymentmidtransservice.app.property.PaymentProperty;
import com.application.paymentmidtransservice.infra.mysql.repository.PaymentRepo;
import com.application.paymentmidtransservice.core.gateway.EmailGateway;
import com.application.paymentmidtransservice.core.gateway.PaymentGateway;
import com.application.paymentmidtransservice.core.gateway.impl.EmailGatewayImpl;
import com.application.paymentmidtransservice.core.gateway.impl.PaymentGatewayImpl;
import com.application.paymentmidtransservice.core.gateway.MidtransGateway;
import com.application.paymentmidtransservice.core.gateway.impl.MidtransGatewayImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public MidtransGateway midtransGateway(PaymentProperty paymentProperty) {
        return new MidtransGatewayImpl(paymentProperty);
    }

    @Bean
    public PaymentGateway paymentGateway(PaymentRepo paymentRepo) {
        return new PaymentGatewayImpl(paymentRepo);
    }

    @Bean
    public EmailGateway emailGateway(EmailProperty emailProperty) {
        return new EmailGatewayImpl(emailProperty);
    }

//    @Bean
//    public PaymentUsecase paymentUsecase(PaymentGateway paymentGateway,
//                                         MidtransGateway midtransGateway,
//                                         EmailGateway emailGateway,
//                                         PlatformTransactionManager platformTransactionManager) {
//        return new VaTransferUsecase(
//            paymentGateway, midtransGateway, emailGateway, platformTransactionManager);
//    }

}
