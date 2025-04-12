package com.funkybooboo.store;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("stripe")
@Primary
public class StripePaymentService implements PaymentService {
    @Value("${stripe.apiUrl}")
    private String apiUrl;

    @Value("${stripe.enabled}")
    private String enabled;

    @Value("${stripe.timeout}")
    private String timeout;

    @Value("${stripe.supported-currencies}")
    private List<String> supportedCurrencies;

    @Override
    public void processPayment(double amount) {
        System.out.println("STRIPE PAYMENT");
        System.out.println("API URL: " + apiUrl);
        System.out.printf("Enabled: %s\n", enabled);
        System.out.printf("Timeout: %s\n", timeout);
        System.out.println("Supported Currencies: " + supportedCurrencies);
        System.out.println("Amount : " + amount);
    }
}
