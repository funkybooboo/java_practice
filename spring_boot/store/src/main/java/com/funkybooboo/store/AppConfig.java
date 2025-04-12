package com.funkybooboo.store;

import com.funkybooboo.store.Notification.EmailNotificationService;
import com.funkybooboo.store.Notification.NotificationService;
import com.funkybooboo.store.Order.OrderService;
import com.funkybooboo.store.Order.PayPalPaymentService;
import com.funkybooboo.store.Order.PaymentService;
import com.funkybooboo.store.Order.StripePaymentService;
import com.funkybooboo.store.User.InMemoryUserRepository;
import com.funkybooboo.store.User.UserRepository;
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
    public UserRepository userRepository() {
        return new InMemoryUserRepository();
    }
}
