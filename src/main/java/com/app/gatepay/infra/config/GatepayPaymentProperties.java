package com.app.gatepay.infra.config;

import com.app.gatepay.shared.enums.PaymentProvider;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "gatepay.payment")
public class GatepayPaymentProperties {

    /** Global default provider (Phase 1: single-tenant). */
    private PaymentProvider defaultProvider = PaymentProvider.MIDTRANS;

    private Providers providers = new Providers();

    @Data
    public static class Providers {
        private Midtrans midtrans = new Midtrans();
    }

    @Data
    public static class Midtrans {
        private String merchantId;
        private String clientKey;
        private String serverKey;
        private String hostname;
        private CoreApi coreApi = new CoreApi();

        @Data
        public static class CoreApi {
            private String charge;
        }
    }
}

