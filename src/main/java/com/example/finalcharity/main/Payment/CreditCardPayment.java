package com.example.finalcharity.main.Payment;

public class CreditCardPayment implements PaymentMethod {
    private String cardNumber;
    private String cardHolder;
    private String expiryDate;
    private String cvv;

    // Constructor
    public CreditCardPayment(String cardNumber, String cardHolder, String expiryDate, String cvv) {
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    public CreditCardPayment() {
    }

    @Override
    public boolean pay() {
        return true;
    }

    @Override
    public boolean validatePaymentDetails() {
        System.out.println("Validating credit card details.");
        return cardNumber != null && cardHolder != null && expiryDate != null && cvv != null;
    }

    @Override
    public boolean refund() {
        System.out.println("Processing credit card refund.");
        return true;
    }

    // Getters and Setters
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}