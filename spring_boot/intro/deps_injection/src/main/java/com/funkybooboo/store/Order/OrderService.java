package com.funkybooboo.store.Order;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

// This service is responsible for handling order-related logic, like processing a payment.
public class OrderService {

    // The service depends on a PaymentService to handle payment processing (could be Stripe, PayPal, etc.)
    private final PaymentService paymentService;

    // Constructor-based dependency injection: Spring (or manual setup) passes in the PaymentService implementation
    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
        System.out.println("OrderService constructed");
    }

    // This method is automatically called by Spring *after* the object is constructed and dependencies are injected.
    // It’s a good place for initialization logic.
    @PostConstruct
    public void init() {
        System.out.println("OrderService created");
    }

    // This method is automatically called *before* the bean is destroyed (e.g., when the app shuts down).
    // Use it to release resources, stop timers, close connections, etc.
    @PreDestroy
    public void cleanup() {
        System.out.println("OrderService destroyed");
    }

    // This method simulates placing an order and delegates payment processing to the PaymentService.
    public void placeOrder() {
        paymentService.processPayment(10); // Simulate a $10 order
    }
}
