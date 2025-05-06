package com.integration.paymentmidtrans.application.config;

import com.integration.paymentmidtrans.application.property.EmailProperty;
import com.integration.paymentmidtrans.application.property.PaymentProperty;
import com.integration.paymentmidtrans.ports.outbound.mysql.repository.PaymentRepositoryPort;
import com.integration.paymentmidtrans.ports.outbound.email.EmailOutboundPort;
import com.integration.paymentmidtrans.ports.outbound.mysql.jpa.PaymentJPAOutboundPort;
import com.integration.paymentmidtrans.adapter.outbound.email.EmailOutboundAdapter;
import com.integration.paymentmidtrans.adapter.outbound.mysql.jpagateway.PaymentGatewayImpl;
import com.integration.paymentmidtrans.ports.outbound.midtrans.MidtransCoreAPIOutboundPort;
import com.integration.paymentmidtrans.adapter.outbound.midtrans.MidtransCoreAPIOutboundAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public MidtransCoreAPIOutboundPort midtransGateway(PaymentProperty paymentProperty) {
        return new MidtransCoreAPIOutboundAdapter(paymentProperty);
    }

    @Bean
    public PaymentJPAOutboundPort paymentGateway(PaymentRepositoryPort paymentRepositoryPort) {
        return new PaymentGatewayImpl(paymentRepositoryPort);
    }

    @Bean
    public EmailOutboundPort emailGateway(EmailProperty emailProperty) {
        return new EmailOutboundAdapter(emailProperty);
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
