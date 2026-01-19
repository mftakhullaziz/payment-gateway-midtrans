package com.app.gatepay.infra.config;

// remove EntityScan usage to avoid compile issues
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.app.gatepay")
public class PersistenceConfig {
}
