package com.funkybooboo.store.Order;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public class StripePaymentService implements PaymentService {
    @Value("${payment.stripe.apiUrl}")
    private String apiUrl;

    @Value("${payment.stripe.enabled}")
    private String enabled;

    @Value("${payment.stripe.timeout}")
    private String timeout;

    @Value("${payment.stripe.supported-currencies}")
    private List<String> supportedCurrencies;

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
