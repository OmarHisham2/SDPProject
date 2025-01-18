package com.example.finalcharity.main.Payment;

public class AuthorizeNetPaymentProcessor extends PaymentProcessor {

    private final AuthorizeNetService authorizeNetService;

    public AuthorizeNetPaymentProcessor(AuthorizeNetService authorizeNetService) {
        this.authorizeNetService = authorizeNetService;
    }

    @Override
    protected boolean authorizePayment(String cardNumber, String expiryDate, String cvv, double amount) {
        System.out.println("Authorizing payment via Authorize.Net...");
        return authorizeNetService.authorizePayment(cardNumber, expiryDate, cvv, amount);
    }

    @Override
    protected void notifyUser() {
        System.out.println("Notifying user about successful Authorize.Net payment via SMS...");
    }
}