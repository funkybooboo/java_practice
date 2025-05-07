package com.funkybooboo.store.Order;

// This interface defines a contract for payment processing.
// Any class (like StripePaymentService or PayPalPaymentService) that implements this interface
// must provide an implementation for the processPayment method.
public interface PaymentService {

    // Method to process a payment for the given amount.
    // Implementing classes will define how the payment is actually processed.
    void processPayment(double amount);
}
