package com.example.finalcharity.main.Payment;

import org.springframework.stereotype.Component;

@Component
public class StripeService implements StripePaymentGateway {

    // Updated method with logging
    @Override
    public boolean processPayment(String cardNumber, String expiryDate, String cvv, double amount) {
        System.out.println("StripeService processPayment method called with cardNumber: " + cardNumber +
                ", expiryDate: " + expiryDate + ", cvv: " + cvv + ", amount: " + amount);
        // Simulate the actual payment processing logic here
        boolean result = true; // Assuming payment processing is successful
        System.out.println("StripeService processPayment method returned: " + result);
        return result;
    }

    // Updated method with logging
    @Override
    public boolean refundPayment(String transactionId) {
        System.out.println("StripeService refundPayment method called with transactionId: " + transactionId);
        // Simulate the actual refund logic here
        boolean result = true; // Assuming refund is successful
        System.out.println("StripeService refundPayment method returned: " + result);
        return result;
    }
}
