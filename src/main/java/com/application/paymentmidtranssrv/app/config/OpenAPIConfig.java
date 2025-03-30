package com.application.paymentmidtranssrv.app.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        List<Server> servers = new ArrayList<>();

        // Try to get Ngrok public URL dynamically
        String ngrokUrl = getNgrokPublicUrl();
        if (ngrokUrl != null) {
            servers.add(new Server().url(ngrokUrl).description("Ngrok tunnel (auto-detected)"));
        }

        // Always add localhost as fallback
        servers.add(new Server().url("http://localhost:8080").description("Local development"));

        return new OpenAPI()
            .info(new Info()
                .title("Payment API")
                .version("v1.0")
                .description("Handles VA Transfer Payment via Midtrans"))
            .servers(servers);
    }

    private String getNgrokPublicUrl() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(new URL("http://127.0.0.1:4040/api/tunnels"));
            for (JsonNode tunnel : root.get("tunnels")) {
                String proto = tunnel.get("proto").asText();
                if ("https".equals(proto)) {
                    return tunnel.get("public_url").asText();
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to get Ngrok URL: " + e.getMessage());
        }
        return null;
    }
}