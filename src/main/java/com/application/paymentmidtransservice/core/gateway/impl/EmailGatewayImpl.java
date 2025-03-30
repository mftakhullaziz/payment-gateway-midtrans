package com.application.paymentmidtransservice.core.gateway.impl;

import com.application.paymentmidtransservice.app.annotation.Gateway;
import com.application.paymentmidtransservice.app.property.EmailProperty;
import com.application.paymentmidtransservice.core.gateway.EmailGateway;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Gateway
@RequiredArgsConstructor
public class EmailGatewayImpl implements EmailGateway {

    private final EmailProperty emailProperty;


    @Override
    public void publishEmailRemainderNotification(String email,
                                                  String name,
                                                  String virtualAccountNumber,
                                                  String bankType,
                                                  String expiredTime,
                                                  String status) {

        String htmlTemplate = loadTemplate("payment-reminder-notification.html");

        // Replace placeholders manually
        String htmlContent = htmlTemplate
            .replace("{{name}}", name)
            .replace("{{bankType}}", bankType)
            .replace("{{virtualAccountNumber}}", virtualAccountNumber)
            .replace("{{status}}", status)
            .replace("{{expiredTime}}", String.valueOf(expiredTime))
            .replace("{{year}}", String.valueOf(java.time.Year.now().getValue()));

        try {
            Session session = createSession();
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(emailProperty.getMailDev().getSender()));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject("Reminder to Payment");
            message.setContent(htmlContent, "text/html; charset=utf-8");

            Transport.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected server error: " + e.getMessage(), e);
        }
    }

    @Override
    public void publishEmailStatusNotification() {

    }

    private Session createSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", emailProperty.getMailDev().getHostname()); // smtp.mailtrap.io
        props.put("mail.smtp.port", emailProperty.getMailDev().getPort());     // 587
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // TLS enabled

        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                    emailProperty.getMailDev().getUsername(),
                    emailProperty.getMailDev().getPassword()
                );
            }
        };

        return Session.getInstance(props, auth);
    }

    private String loadTemplate(String templateName) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("templates/" + templateName)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Template not found: " + templateName);
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load email template: " + e.getMessage(), e);
        }
    }
}
