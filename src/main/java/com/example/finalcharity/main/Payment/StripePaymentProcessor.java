package com.example.finalcharity.main.Payment;

public class StripePaymentProcessor extends PaymentProcessor {

    private final StripeService stripeService;

    public StripePaymentProcessor(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @Override
    protected boolean authorizePayment(String cardNumber, String expiryDate, String cvv, double amount) {
        System.out.println("Authorizing payment via Stripe...");
        
        return stripeService.processPayment(cardNumber, expiryDate, cvv, amount);
    }

    @Override
    protected void notifyUser() {
        System.out.println("Notifying user about successful Stripe payment via email...");
    }
}
