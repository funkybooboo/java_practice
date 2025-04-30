package com.funkybooboo.store;

import com.funkybooboo.store.entities.User;
import com.funkybooboo.store.repositories.UserRepository;
import com.funkybooboo.store.services.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class StoreApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(StoreApplication.class, args);
        var service = context.getBean(UserService.class);
        service.showEntityStates();
        service.showRelatedEntities();
    }
}
