package com.app.gatepay.shared.resources;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(value = "application.external-service.email-gateway")
public class EmailResource {

    private MailDev mailDev;

    @Data
    public static class MailDev {
        private String hostname;
        private String port;
        private String username;
        private String password;
        private String sender;
    }
}
