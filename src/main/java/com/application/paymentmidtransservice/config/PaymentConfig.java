package com.application.paymentmidtransservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(value = "application.external-service.payment-gateway")
public class PaymentConfig {

    private Midtrans midtrans;

    @Data
    public static class Midtrans {
        private String merchantId;
        private String clientKey;
        private String serverKey;
        private String paymentUri;
        private Disbursement disbursement;
    }

    @Data
    public static class Disbursement {
        private String beneficiaries;
        private String patchBeneficiaries;
        private String listBeneficiaries;
        private String payouts;
        private String approvePayouts;
        private String rejectPayouts;
        private String detailPayouts;
        private String transactionDetails;
    }
}
