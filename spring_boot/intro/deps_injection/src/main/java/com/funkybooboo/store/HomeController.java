package com.funkybooboo.store;

// Import necessary Spring framework annotations and classes
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// Marks this class as a Spring MVC Controller that can handle web requests
@Controller
public class HomeController {

    // Injects the value of the property `spring.application.name` from application.properties or application.yml
    @Value("${spring.application.name}")
    private String appName;

    // Maps HTTP requests with the root URL "/" to this method
    @RequestMapping("/")
    public String index() {
        // Print the application name to the console for debugging
        System.out.println("appName:" + appName);

        // Returns the name of the HTML file to serve (index.html)
        // This file should be placed under src/main/resources/static or templates (if using Thymeleaf)
        return "index.html";
    }
}
