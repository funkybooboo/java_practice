package com.funkybooboo.store;

import com.funkybooboo.store.Notification.NotificationService;
import com.funkybooboo.store.Order.OrderService;
import com.funkybooboo.store.User.User;
import com.funkybooboo.store.User.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class StoreApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(StoreApplication.class, args);

        var orderService = context.getBean(OrderService.class);
        orderService.placeOrder();

        var userService = context.getBean(UserService.class);
        userService.registerUser(new User(1L, "hello@me.com", "asdfaf", "Tom"));

        context.close();
    }
}
