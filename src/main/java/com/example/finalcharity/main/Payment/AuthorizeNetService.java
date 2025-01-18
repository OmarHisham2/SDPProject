package com.example.finalcharity.main.Payment;

import org.springframework.stereotype.Component;

@Component
public class AuthorizeNetService implements AuthorizeNetPaymentGateway {

    // Updated method with logging for the payment authorization
    @Override
    public boolean authorizePayment(String cardNumber, String expiryDate, String cvv, double amount) {
        System.out.println("AuthorizeNetService authorizePayment method called with cardNumber: " + cardNumber +
                ", expiryDate: " + expiryDate + ", cvv: " + cvv + ", amount: " + amount);
        // Simulate the actual authorization logic here
        boolean result = true; // Assuming authorization is successful
        System.out.println("AuthorizeNetService authorizePayment method returned: " + result);
        return result;
    }

    // Updated method with logging for the payment voiding (refund)
    @Override
    public boolean voidPayment(String transactionId) {
        System.out.println("AuthorizeNetService voidPayment method called with transactionId: " + transactionId);
        // Simulate the actual void (refund) logic here
        boolean result = true; // Assuming void payment is successful
        System.out.println("AuthorizeNetService voidPayment method returned: " + result);
        return result;
    }
}
