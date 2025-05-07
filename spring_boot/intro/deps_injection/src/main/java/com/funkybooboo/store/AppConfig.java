package com.funkybooboo.store;

import com.funkybooboo.store.Notification.EmailNotificationService;
import com.funkybooboo.store.Notification.NotificationService;
import com.funkybooboo.store.Order.OrderService;
import com.funkybooboo.store.Order.PayPalPaymentService;
import com.funkybooboo.store.Order.PaymentService;
import com.funkybooboo.store.Order.StripePaymentService;
import com.funkybooboo.store.User.InMemoryUserRepository;
import com.funkybooboo.store.User.UserRepository;
import com.funkybooboo.store.User.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

// This class is marked as a @Configuration, meaning it's a source of bean definitions for Spring.
// Beans defined here will be created and managed by the Spring container.
@Configuration
public class AppConfig {

    // Injects the value of the property 'payment.gateway' from application.properties or application.yml.
    // Expected values: "stripe" or "paypal"
    @Value("${payment.gateway}")
    private String paymentGateway;

    // Defines a Spring bean for PaymentService.
    // Chooses the implementation based on the 'payment.gateway' property.
    @Bean
    public PaymentService paymentService() {
        if (Objects.equals(paymentGateway, "stripe")) {
            return new StripePaymentService();
        }

        if (Objects.equals(paymentGateway, "paypal")) {
            return new PayPalPaymentService();
        }

        // If the value is not recognized, throw an error to prevent misconfiguration.
        throw new IllegalArgumentException("Unsupported payment gateway: " + paymentGateway);
    }

    // Defines a bean for OrderService, injecting the chosen PaymentService implementation.
    @Bean
    public OrderService orderService() {
        return new OrderService(paymentService());
    }

    // Defines a bean for NotificationService.
    // This uses an email-based implementation.
    @Bean
    public NotificationService notificationService() {
        return new EmailNotificationService();
    }

    // Defines a bean for the user repository.
    // This is an in-memory implementation, useful for testing or simple apps.
    @Bean
    public UserRepository userRepository() {
        return new InMemoryUserRepository();
    }

    // Defines a bean for UserService, injecting the repository and notification service.
    @Bean
    public UserService userService() {
        return new UserService(userRepository(), notificationService());
    }
}
