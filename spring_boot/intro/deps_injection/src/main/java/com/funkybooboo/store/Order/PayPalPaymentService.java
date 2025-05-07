package com.funkybooboo.store.Order;

import org.springframework.beans.factory.annotation.Value;
import java.util.List;

// This class implements the PaymentService interface.
// It provides a concrete implementation for handling payments via PayPal.
public class PayPalPaymentService implements PaymentService {

    // Injects the PayPal API URL from application.properties or application.yml
    @Value("${payment.paypal.apiUrl}")
    private String apiUrl;

    // Injects whether PayPal payments are enabled (as a string here, could be "true"/"false")
    @Value("${payment.paypal.enabled}")
    private String enabled;

    // Injects the timeout setting for PayPal payment processing (e.g., "45s")
    @Value("${payment.paypal.timeout}")
    private String timeout;

    // Injects a list of supported currency codes for PayPal (e.g., ["USD", "GBP"])
    @Value("${payment.paypal.supported-currencies}")
    private List<String> supportedCurrencies;

    // Implements the method declared in the PaymentService interface.
    // Simulates processing a payment using the PayPal settings injected above.
    @Override
    public void processPayment(double amount) {
        System.out.println("PAYPAL PAYMENT");
        System.out.println("API URL: " + apiUrl);
        System.out.println("Enabled: " + enabled);
        System.out.println("Timeout: " + timeout);
        System.out.println("Supported Currencies: " + supportedCurrencies);
        System.out.println("Amount : " + amount);
    }
}
