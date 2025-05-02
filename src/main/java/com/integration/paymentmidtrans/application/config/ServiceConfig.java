package com.integration.paymentmidtrans.application.config;

import com.integration.paymentmidtrans.application.property.EmailProperty;
import com.integration.paymentmidtrans.application.property.PaymentProperty;
import com.integration.paymentmidtrans.core.ports.outbound.repository.PaymentRepo;
import com.integration.paymentmidtrans.core.ports.outbound.EmailGateway;
import com.integration.paymentmidtrans.core.ports.outbound.PaymentGateway;
import com.integration.paymentmidtrans.adapter.outbound.email.EmailGatewayImpl;
import com.integration.paymentmidtrans.adapter.outbound.mysql.jpagateway.PaymentGatewayImpl;
import com.integration.paymentmidtrans.core.ports.outbound.MidtransGateway;
import com.integration.paymentmidtrans.adapter.outbound.midtrans.MidtransGatewayImpl;
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
