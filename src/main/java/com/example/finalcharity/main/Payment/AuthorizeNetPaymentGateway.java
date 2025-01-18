package com.example.finalcharity.main.Payment;

//  AuthorizeNetPaymentGateway.java
public interface AuthorizeNetPaymentGateway {

    boolean authorizePayment(String cardNumber, String expiryDate, String cvv, double amount);
    boolean voidPayment(String transactionId);
}