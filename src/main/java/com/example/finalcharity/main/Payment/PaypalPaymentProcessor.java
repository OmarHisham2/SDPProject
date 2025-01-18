package com.example.finalcharity.main.Payment;

public class PaypalPaymentProcessor extends PaymentProcessor {

    @Override
    protected boolean authorizePayment(String cardNumber, String expiryDate, String cvv, double amount) {
        System.out.println("Authorizing payment via PayPal...");
        return true;
    }

    @Override
    protected void notifyUser() {
        System.out.println("Notifying user about successful PayPal payment via email...");
    }
}