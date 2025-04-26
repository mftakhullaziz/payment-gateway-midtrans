package com.application.paymentmidtranssrv.facade.config;

import com.application.paymentmidtranssrv.facade.property.EmailProperty;
import com.application.paymentmidtranssrv.facade.property.PaymentProperty;
import com.application.paymentmidtranssrv.infrastructure.mysql.repository.PaymentRepo;
import com.application.paymentmidtranssrv.core.gateway.EmailGateway;
import com.application.paymentmidtranssrv.core.gateway.PaymentGateway;
import com.application.paymentmidtranssrv.core.gateway.impl.EmailGatewayImpl;
import com.application.paymentmidtranssrv.core.gateway.impl.PaymentGatewayImpl;
import com.application.paymentmidtranssrv.core.gateway.MidtransGateway;
import com.application.paymentmidtranssrv.core.gateway.impl.MidtransGatewayImpl;
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
