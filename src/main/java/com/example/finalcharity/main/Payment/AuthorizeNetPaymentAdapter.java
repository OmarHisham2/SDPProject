package com.example.finalcharity.main.Payment;

public class AuthorizeNetPaymentAdapter implements PaymentMethod, AuthorizeNetPaymentGateway {

    private final AuthorizeNetService authorizeNetService;
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    private double amount;

    // Constructor with logging
    public AuthorizeNetPaymentAdapter(AuthorizeNetService authorizeNetService, String cardNumber, String expiryDate, String cvv, double amount) {
        System.out.println("AuthorizeNetPaymentAdapter constructor called with authorizeNetService: " + authorizeNetService +
                ", cardNumber: " + cardNumber + ", expiryDate: " + expiryDate + ", cvv: " + cvv + ", amount: " + amount);
        this.authorizeNetService = authorizeNetService;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.amount = amount;
    }

    // Pay method with logging
    @Override
    public boolean pay() {
        System.out.println("AuthorizeNetPaymentAdapter pay method called with cardNumber: " + cardNumber +
                ", expiryDate: " + expiryDate + ", cvv: " + cvv + ", amount: " + amount);
        boolean result = authorizeNetService.authorizePayment(cardNumber, expiryDate, cvv, amount);
        System.out.println("AuthorizeNetPaymentAdapter pay method call returned: " + result);
        return result;
    }

    // Validate payment details with logging
    @Override
    public boolean validatePaymentDetails() {
        System.out.println("Validating authorize.net payment details.");
        return true; // Assuming validation logic is here
    }

    // Refund method with logging
    @Override
    public boolean refund() {
        System.out.println("AuthorizeNetPaymentAdapter refund method called");
        boolean result = authorizeNetService.voidPayment("123"); // Using a static transaction ID for the demo
        System.out.println("AuthorizeNetPaymentAdapter refund method call returned: " + result);
        return result;
    }

    // Authorize payment method with logging
    @Override
    public boolean authorizePayment(String cardNumber, String expiryDate, String cvv, double amount) {
        System.out.println("AuthorizeNetPaymentAdapter authorizePayment method called with cardNumber: " + cardNumber +
                ", expiryDate: " + expiryDate + ", cvv: " + cvv + ", amount: " + amount);
        boolean result = authorizeNetService.authorizePayment(cardNumber, expiryDate, cvv, amount);
        System.out.println("AuthorizeNetPaymentAdapter authorizePayment method call returned: " + result);
        return result;
    }

    // Void payment method with logging
    @Override
    public boolean voidPayment(String transactionId) {
        System.out.println("AuthorizeNetPaymentAdapter voidPayment method called with transactionId: " + transactionId);
        boolean result = authorizeNetService.voidPayment(transactionId);
        System.out.println("AuthorizeNetPaymentAdapter voidPayment method call returned: " + result);
        return result;
    }
}
