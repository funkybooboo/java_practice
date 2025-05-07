package com.funkybooboo.store.Notification;

// This class implements the NotificationService interface.
// It provides a concrete way to send notifications via SMS.
public class SMSNotificationService implements NotificationService {

    // Implements the send method from the interface.
    // For now, it just simulates sending an SMS by printing to the console.
    @Override
    public void send(String message, String recipientEmails) {
        // In a real-world app, you would use an SMS API like Twilio here.
        System.out.println("Sending SMS: " + message);
    }
}
