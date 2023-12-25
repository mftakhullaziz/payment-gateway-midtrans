package com.application.paymentmidtransservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(value = "application.payment-gateway")
public class PaymentConfig {

    private Midtrans midtrans;

    @Data
    public static class Midtrans {
        private String merchantId;
        private String clientKey;
        private String serverKey;
        private String paymentUri;
    }
}
