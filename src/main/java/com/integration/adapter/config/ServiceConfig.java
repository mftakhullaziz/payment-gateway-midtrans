package com.integration.adapter.config;

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
