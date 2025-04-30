package com.integration.paymentmidtrans.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(0, stringHttpMessageConverter());
    }

    @Bean
    public StringHttpMessageConverter stringHttpMessageConverter() {
        StringHttpMessageConverter messageConverter = new StringHttpMessageConverter();
        messageConverter.setSupportedMediaTypes(List.of(
            MediaType.APPLICATION_JSON,
            MediaType.TEXT_PLAIN,
            MediaType.ALL
        ));
        return messageConverter;
    }

    private final ServerUrlResolver serverUrlResolver;

    public WebConfig(ServerUrlResolver serverUrlResolver) {
        this.serverUrlResolver = serverUrlResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(serverUrlResolver);
    }
}
