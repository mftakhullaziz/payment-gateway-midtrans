package com.application.paymentmidtranssrv.app.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.application.paymentmidtranssrv.infra.mysql.repository")
@EntityScan(basePackages = "com.application.paymentmidtranssrv.infra.mysql.entity")
public class PersistenceConfig {
}
