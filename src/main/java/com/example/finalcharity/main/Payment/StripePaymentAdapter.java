package com.example.finalcharity.main.Payment;

public class StripePaymentAdapter implements PaymentMethod, StripePaymentGateway {

    private final StripeService stripeService;

    private String cardNumber;
    private String expiryDate;
    private String cvv;
    private double amount;

    // Constructor with logging
    public StripePaymentAdapter(StripeService stripeService, String cardNumber, String expiryDate, String cvv, double amount) {
        System.out.println("StripePaymentAdapter constructor called with stripeService: " + stripeService +
                ", cardNumber: " + cardNumber + ", expiryDate: " + expiryDate + ", cvv: " + cvv + ", amount: " + amount);
        this.stripeService = stripeService;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.amount = amount;
    }

    // Pay method with logging
    @Override
    public boolean pay() {
        System.out.println("StripePaymentAdapter pay method called with cardNumber: " + cardNumber +
                ", expiryDate: " + expiryDate + ", cvv: " + cvv + ", amount: " + amount);
        boolean result = stripeService.processPayment(cardNumber, expiryDate, cvv, amount);
        System.out.println("StripePaymentAdapter pay method call returned: " + result);
        return result;
    }

    // Validate payment details with logging
    @Override
    public boolean validatePaymentDetails() {
        System.out.println("Validating stripe payment details.");
        return true; // Assuming validation is done here, add real validation logic if necessary
    }

    // Refund method with logging
    @Override
    public boolean refund() {
        System.out.println("StripePaymentAdapter refund method called");
        boolean result = stripeService.refundPayment("123"); // A static transaction ID used here for demo purposes
        System.out.println("StripePaymentAdapter refund method call returned: " + result);
        return result;
    }

    // Process payment with logging
    @Override
    public boolean processPayment(String cardNumber, String expiryDate, String cvv, double amount) {
        System.out.println("StripePaymentAdapter processPayment method called with cardNumber: " + cardNumber +
                ", expiryDate: " + expiryDate + ", cvv: " + cvv + ", amount: " + amount);
        boolean result = stripeService.processPayment(cardNumber, expiryDate, cvv, amount);
        System.out.println("StripePaymentAdapter processPayment method call returned: " + result);
        return result;
    }

    // Refund payment with logging
    @Override
    public boolean refundPayment(String transactionId) {
        System.out.println("StripePaymentAdapter refundPayment method called with transactionId: " + transactionId);
        boolean result = stripeService.refundPayment(transactionId);
        System.out.println("StripePaymentAdapter refundPayment method call returned: " + result);
        return result;
    }
}
