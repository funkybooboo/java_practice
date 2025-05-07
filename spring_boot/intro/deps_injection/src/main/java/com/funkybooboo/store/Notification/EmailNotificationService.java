package com.funkybooboo.store.Notification;

import org.springframework.beans.factory.annotation.Value;

// This class implements the NotificationService interface.
// It simulates sending an email notification.
public class EmailNotificationService implements NotificationService {

    // Injects the email host (e.g., smtp.example.com) from application.properties or application.yml
    @Value("${email.host}")
    private String host;

    // Injects the email server port (e.g., 587) from config
    @Value("${email.port}")
    private String port;

    // Implements the send method from NotificationService.
    // This simulates sending an email by printing out the configuration and message details.
    @Override
    public void send(String message, String recipientEmail) {
        System.out.println("Recipient: " + recipientEmail);
        System.out.println("Message: " + message);
        System.out.println("Host: " + host);
        System.out.println("Port: " + port);
    }
}
