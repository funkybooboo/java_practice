package com.funkybooboo.store;

import com.funkybooboo.store.entities.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class StoreApplication {
    public static void main(String[] args) {
//        ConfigurableApplicationContext context = SpringApplication.run(StoreApplication.class, args);
//        
//        context.close();
        
        var user1 = new User();
        user1.setName("name");
        user1.setEmail("email");
        user1.setPassword("password");

        var user2 = new User(1L, "name", "email", "password");

        var user3 = User.builder().name("name").email("email").password("password").build();
    }
}
