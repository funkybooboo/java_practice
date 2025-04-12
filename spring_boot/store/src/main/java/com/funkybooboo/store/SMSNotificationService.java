package com.funkybooboo.store;

public class SMSNotificationService implements NotificationService {
    @Override
    public void send(String message) {
        System.out.println("Sending SMS: " + message);
    }
}
