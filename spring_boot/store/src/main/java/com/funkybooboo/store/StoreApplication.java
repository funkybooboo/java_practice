package com.funkybooboo.store;

import com.funkybooboo.store.Notification.NotificationManager;
import com.funkybooboo.store.Order.OrderService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class StoreApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(StoreApplication.class, args);

        var orderService = context.getBean(OrderService.class);
        orderService.placeOrder();

        var notificationManager = context.getBean(NotificationManager.class);
        notificationManager.sendNotification("Order Placed");

        context.close();
    }
}
