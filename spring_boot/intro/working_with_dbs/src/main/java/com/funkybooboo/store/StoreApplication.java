package com.funkybooboo.store;

// These are our own application classes
import com.funkybooboo.store.entities.User;
import com.funkybooboo.store.repositories.UserRepository;
import com.funkybooboo.store.services.UserService;

// Spring Boot and Spring Framework imports
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Main entry point for the Spring Boot application.
 *
 * The @SpringBootApplication annotation enables component scanning, auto-configuration,
 * and allows us to define extra configuration if needed. It is a combination of:
 *   - @Configuration: lets us register Spring beans
 *   - @EnableAutoConfiguration: enables Spring Boot’s auto-configuration mechanism
 *   - @ComponentScan: allows Spring to scan for components in the package and subpackages
 */
@SpringBootApplication
public class StoreApplication {

    /**
     * This is the main method, the starting point of the Java application.
     * SpringApplication.run(...) boots the Spring Boot app:
     *  - Starts the Spring application context (IoC container)
     *  - Auto-configures beans
     *  - Performs classpath scanning for @Component, @Service, @Repository, etc.
     */
    public static void main(String[] args) {
        // Start the application and get the Spring context
        ConfigurableApplicationContext context = SpringApplication.run(StoreApplication.class, args);

        // From the context, retrieve the UserService bean.
        // This is Spring's way of dependency injection.
        var service = context.getBean(UserService.class);

        // Example usage: Fetch paginated products from the service
        // You would normally do this in a controller or scheduled task, not in main()
        service.fetchPaginatedProducts(0, 10);

        // Gracefully shut down the Spring context (optional here, but good habit for CLI apps)
        context.close();
    }
}
