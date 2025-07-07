package com.integration.paymentmidtrans.application.config;

import com.integration.paymentmidtrans.adapter.outbound.mysql.jpa.CustomerJpaAdapter;
import com.integration.paymentmidtrans.application.property.EmailProperty;
import com.integration.paymentmidtrans.application.property.PaymentProperty;
import com.integration.paymentmidtrans.ports.outbound.mysql.jpa.CustomerJPAOutboundPort;
import com.integration.paymentmidtrans.ports.outbound.mysql.repository.CustomerRepositoryPort;
import com.integration.paymentmidtrans.ports.outbound.mysql.repository.PaymentRepositoryPort;
import com.integration.paymentmidtrans.ports.outbound.email.EmailOutboundPort;
import com.integration.paymentmidtrans.ports.outbound.mysql.jpa.PaymentJPAOutboundPort;
import com.integration.paymentmidtrans.adapter.outbound.email.EmailOutboundAdapter;
import com.integration.paymentmidtrans.adapter.outbound.mysql.jpa.PaymentJpaAdapter;
import com.integration.paymentmidtrans.ports.outbound.midtrans.MidtransCoreAPIOutboundPort;
import com.integration.paymentmidtrans.adapter.outbound.midtrans.MidtransCoreAPIOutboundAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

//    @Bean
//    public MidtransCoreAPIOutboundPort midtransGateway(PaymentProperty paymentProperty) {
//        return new MidtransCoreAPIOutboundAdapter(paymentProperty);
//    }
//
//    @Bean
//    public PaymentJPAOutboundPort paymentGateway(PaymentRepositoryPort paymentRepositoryPort) {
//        return new PaymentJpaAdapter(paymentRepositoryPort);
//    }

//    @Bean
//    public CustomerJPAOutboundPort customerGateway(CustomerRepositoryPort customerRepositoryPort) {
//        return new CustomerJpaAdapter(customerRepositoryPort);
//    }

//    @Bean
//    public EmailOutboundPort emailGateway(EmailProperty emailProperty) {
//        return new EmailOutboundAdapter(emailProperty);
//    }

//    @Bean
//    public APICorePaymentUCAdapter apiCorePaymentUCAdapter(
//        CustomerJPAOutboundPort customerPort,
//        MidtransCoreAPIOutboundPort midtransCoreAPIPort,
//        EmailOutboundPort emailPort,
//        PaymentJPAOutboundPort paymentPort
//    ) {
//        return new APICorePaymentUCPort(customerPort, midtransCoreAPIPort, emailPort, paymentPort);
//    }

//    @Bean
//    public PaymentUsecase paymentUsecase(PaymentGateway paymentGateway,
//                                         MidtransGateway midtransGateway,
//                                         EmailGateway emailGateway,
//                                         PlatformTransactionManager platformTransactionManager) {
//        return new VaTransferUsecase(
//            paymentGateway, midtransGateway, emailGateway, platformTransactionManager);
//    }

}
