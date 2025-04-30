package com.funkybooboo.store;

import com.funkybooboo.store.entities.User;
import com.funkybooboo.store.repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class StoreApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(StoreApplication.class, args);
        var userRepository = context.getBean(UserRepository.class);
        
        // save
//        var user = User.builder().name("Nate").email("nate.stott@pm.me").password("password").build();
//        userRepository.save(user);
        
        // get by id
//        var user = userRepository.findById(1L).orElseThrow();
//        System.out.println(user);
        
        // get all
//        userRepository.findAll().forEach(System.out::println);
        
        // delete by id
//        userRepository.deleteById(1L);
    }
}
