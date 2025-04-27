package com.funkybooboo.store.Order;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public class PayPalPaymentService implements PaymentService {
    @Value("${payment.paypal.apiUrl}")
    private String apiUrl;

    @Value("${payment.paypal.enabled}")
    private String enabled;

    @Value("${payment.paypal.timeout}")
    private String timeout;

    @Value("${payment.paypal.supported-currencies}")
    private List<String> supportedCurrencies;

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
