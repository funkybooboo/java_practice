package com.funkybooboo.store;

import com.funkybooboo.store.entities.Address;
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

        var user = User.builder().name("name").email("email").password("password").build();
        
        var address = Address.builder().street("street").city("city").state("state").zip("zip").build();
        
        user.addAddress(address);

        System.out.println(user);
        System.out.println(address);
    }
}
