package com.funkybooboo.store;

public class EmailNotificationService implements NotificationService {
    @Override
    public void send(String message) {
        System.out.println("Sending email: " + message);
    }
}
