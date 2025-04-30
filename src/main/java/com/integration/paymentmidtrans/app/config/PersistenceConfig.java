package com.integration.paymentmidtrans.app.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.application.paymentmidtranssrv.infrastructure.mysql.repository")
@EntityScan(basePackages = "com.application.paymentmidtranssrv.infra.mysql.entity")
public class PersistenceConfig {
}
