package com.integration.paymentmidtrans.app.config;

import com.integration.paymentmidtrans.app.property.EmailProperty;
import com.integration.paymentmidtrans.app.property.PaymentProperty;
import com.integration.paymentmidtrans.adapter.outbound.mysql.repository.PaymentRepo;
import com.integration.paymentmidtrans.port.inbound.jpagateway.EmailGateway;
import com.integration.paymentmidtrans.port.inbound.jpagateway.PaymentGateway;
import com.integration.paymentmidtrans.adapter.inbound.emailgateway.EmailGatewayImpl;
import com.integration.paymentmidtrans.adapter.inbound.jpagateway.PaymentGatewayImpl;
import com.integration.paymentmidtrans.port.inbound.midtransgateway.MidtransGateway;
import com.integration.paymentmidtrans.adapter.inbound.midtransgateway.MidtransGatewayImpl;
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
