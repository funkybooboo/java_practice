package com.funkybooboo.store;

// Importing required classes from your project and Spring Boot framework
import com.funkybooboo.store.Notification.NotificationService;
import com.funkybooboo.store.Order.OrderService;
import com.funkybooboo.store.User.User;
import com.funkybooboo.store.User.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

// This annotation tells Spring Boot to start scanning for components (like @Service, @Controller, etc.)
// and set up everything needed to run the application.
@SpringBootApplication
public class StoreApplication {

    // This is the main method — it is the entry point of any Java application.
    public static void main(String[] args) {
        // SpringApplication.run() bootstraps the application.
        // It sets up the Spring context (a container that manages all the beans and services).
        ConfigurableApplicationContext context = SpringApplication.run(StoreApplication.class, args);

        // Retrieving an instance of OrderService from the Spring context.
        // Spring automatically creates and manages this instance for you.
        var orderService = context.getBean(OrderService.class);

        // Calling the placeOrder() method — this might handle placing an order.
        orderService.placeOrder();

        // Retrieving the UserService bean from the context.
        var userService = context.getBean(UserService.class);

        // Creating a new User object and registering it using the userService.
        // User has fields: id (1L), email, password, and name.
        userService.registerUser(new User(1L, "hello@me.com", "asdfaf", "Tom"));

        // Closing the Spring application context gracefully.
        context.close();
    }
}
