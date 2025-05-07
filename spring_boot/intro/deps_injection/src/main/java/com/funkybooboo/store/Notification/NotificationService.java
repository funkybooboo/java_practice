package com.funkybooboo.store.Notification;

// This interface defines a contract for sending notifications.
// Any class (like EmailNotificationService or SMSNotificationService) that implements this
// must define how to send a message to a recipient.
public interface NotificationService {

    // Sends a message to the specified recipient.
    // The actual behavior depends on the implementation (e.g., send email or SMS).
    void send(String message, String recipientEmail);
}
