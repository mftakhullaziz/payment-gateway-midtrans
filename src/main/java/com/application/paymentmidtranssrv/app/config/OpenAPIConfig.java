package com.application.paymentmidtranssrv.app.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Server httpsNgrokServer = new Server();
        httpsNgrokServer.setUrl("https://d563-114-10-103-236.ngrok-free.app");
        httpsNgrokServer.setDescription("Ngrok tunnel - Production endpoint");

        Server localDevServer = new Server();
        localDevServer.setUrl("http://localhost:8080");
        localDevServer.setDescription("Local development environment");

        return new OpenAPI()
            .info(new Info()
                .title("Payment API")
                .version("v1.0")
                .description("Handles VA Transfer Payment via Midtrans"))
            .servers(List.of(httpsNgrokServer, localDevServer));
    }
}
