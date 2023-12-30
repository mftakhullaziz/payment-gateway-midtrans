package com.application.paymentmidtransservice.core.service.impl;

import com.application.paymentmidtransservice.config.EmailConfig;
import com.application.paymentmidtransservice.core.service.EmailGateway;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@RequiredArgsConstructor
public class EmailService implements EmailGateway {

    private final EmailConfig emailConfig;

    @Override
    public void publishEmailNotification(String email, String name) {
        try {
            Session session = createSession();
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(emailConfig.getMailDev().getSender()));
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(email));
            message.setSubject("REMINDER TO PAYMENT");
            message.setContent("", "text/html; charset=utf-8");

            Transport.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Session createSession() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", emailConfig.getMailDev().getHostname());
        properties.put("mail.smtp.port", emailConfig.getMailDev().getPort());
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "false");

        System.out.println(properties.get("mail.smtp.port"));
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                    emailConfig.getMailDev().getUsername(),
                    emailConfig.getMailDev().getPassword()
                );
            }
        };

        return Session.getInstance(properties, authenticator);
    }

}
