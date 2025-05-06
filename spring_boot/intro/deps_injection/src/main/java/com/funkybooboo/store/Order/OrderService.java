package com.funkybooboo.store.Order;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

public class OrderService {
    private final PaymentService paymentService;

    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
        System.out.println("OrderService constructed");
    }

    @PostConstruct
    public void init() {
        System.out.println("OrderService created");
    }

    @PreDestroy
    public void cleanup() {
        System.out.println("OrderService destroyed");
    }

    public void placeOrder() {
        paymentService.processPayment(10);
    }
}
