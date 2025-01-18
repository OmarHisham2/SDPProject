package com.example.finalcharity.main.Payment;

//  StripePaymentGateway.java
public interface StripePaymentGateway {

    boolean processPayment(String cardNumber, String expiryDate, String cvv, double amount);
    boolean refundPayment(String transactionId);

}