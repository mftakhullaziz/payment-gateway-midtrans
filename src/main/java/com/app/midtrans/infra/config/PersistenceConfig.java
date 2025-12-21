package com.app.midtrans.infra.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.integration.paymentmidtrans.ports.outbound.mysql.repository")
@EntityScan(basePackages = "com.integration.paymentmidtrans.adapter.outbound.mysql.entity")
public class PersistenceConfig {
}
