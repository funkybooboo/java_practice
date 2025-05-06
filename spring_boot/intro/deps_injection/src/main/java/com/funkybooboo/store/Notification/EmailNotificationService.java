package com.funkybooboo.store.Notification;

import org.springframework.beans.factory.annotation.Value;

public class EmailNotificationService implements NotificationService {
    @Value("${email.host}")
    private String host;

    @Value("${email.port}")
    private String port;

    @Override
    public void send(String message, String recipientEmail) {
        System.out.println("Recipient: " + recipientEmail);
        System.out.println("Message: " + message);
        System.out.println("Host: " + host);
        System.out.println("Port: " + port);
    }
}
