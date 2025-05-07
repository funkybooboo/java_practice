package com.funkybooboo.store.Order;

import org.springframework.beans.factory.annotation.Value;
import java.util.List;

// This class implements the PaymentService interface,
// meaning it provides a concrete implementation for processing payments using Stripe.
public class StripePaymentService implements PaymentService {

    // Injects the Stripe API URL from the application.properties or application.yml file
    @Value("${payment.stripe.apiUrl}")
    private String apiUrl;

    // Injects whether Stripe payments are enabled (true/false as a string here)
    @Value("${payment.stripe.enabled}")
    private String enabled;

    // Injects the timeout setting (e.g., "30s") for Stripe payment requests
    @Value("${payment.stripe.timeout}")
    private String timeout;

    // Injects a list of supported currency codes (e.g., ["USD", "EUR"]) from config
    @Value("${payment.stripe.supported-currencies}")
    private List<String> supportedCurrencies;

    // Implements the processPayment method from the PaymentService interface.
    // This method simulates handling a payment using the Stripe configuration values.
    @Override
    public void processPayment(double amount) {
        System.out.println("STRIPE PAYMENT");
        System.out.println("API URL: " + apiUrl);
        System.out.println("Enabled: " + enabled);
        System.out.println("Timeout: " + timeout);
        System.out.println("Supported Currencies: " + supportedCurrencies);
        System.out.println("Amount : " + amount);
    }
}
