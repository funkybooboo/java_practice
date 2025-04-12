package com.funkybooboo.store;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class AppConfig {
    @Value("${payment.gateway}")
    private String paymentGateway;

    @Bean
    public PaymentService paymentService() {
        if (Objects.equals(paymentGateway, "stripe")) {
            return new StripePaymentService();
        }

        if (Objects.equals(paymentGateway, "paypal")) {
            return new PayPalPaymentService();
        }

        throw new IllegalArgumentException("Unsupported payment gateway: " + paymentGateway);
    }

    @Bean
    public OrderService orderService() {
        return new OrderService(paymentService());
    }

    @Bean
    public NotificationService notificationService() {
        return new EmailNotificationService();
    }

    @Bean
    public NotificationManager notificationManager() {
        return new NotificationManager(notificationService());
    }
}
