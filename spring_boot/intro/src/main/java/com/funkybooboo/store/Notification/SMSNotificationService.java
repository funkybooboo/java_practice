package com.funkybooboo.store.Notification;

public class SMSNotificationService implements NotificationService {
    @Override
    public void send(String message, String recipientEmails) {
        System.out.println("Sending SMS: " + message);
    }
}
