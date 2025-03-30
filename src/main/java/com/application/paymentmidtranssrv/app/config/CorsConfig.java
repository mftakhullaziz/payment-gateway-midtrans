package com.application.paymentmidtranssrv.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("*")
            .allowedOrigins("*") // Or specify exact domains: "https://midtrans.com"
            .allowedMethods("*, POST, GET, OPTIONS, DELETE, PUT, PATCH")
            .allowedHeaders("*")
            .allowCredentials(false);
    }
}
