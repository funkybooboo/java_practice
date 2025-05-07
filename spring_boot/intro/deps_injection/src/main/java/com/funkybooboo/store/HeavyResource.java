package com.funkybooboo.store;

// Import annotations used for Spring-managed services and lazy initialization
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

// Marks this class as a service component in Spring.
// Spring will manage its lifecycle and make it available for dependency injection.
@Service

// Tells Spring to create this bean *lazily*, meaning it will not be created at application startup.
// Instead, it will only be created when it is first needed (injected or requested).
@Lazy
public class HeavyResource {

    // Constructor: runs when the object is created
    public HeavyResource() {
        // This message will only print when the bean is actually created
        System.out.println("HeavyResource Created");
    }
}
