package com.example.finalcharity.main.Payment;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

// PaymentMethod Interface
public interface PaymentMethod {
    boolean pay();
    boolean validatePaymentDetails();
    boolean refund();

}

class CreditCardPayment implements PaymentMethod {

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
    public boolean pay( ) {
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

class PaypalPayment implements PaymentMethod {


    private String email;
    private String password;

    // Constructor
    public PaypalPayment(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public PaypalPayment() {
    }

    @Override
    public boolean pay() {
        return true;
    }

    @Override
    public boolean validatePaymentDetails() {
        System.out.println("Validating PayPal account details.");
        return email != null && password != null;
    }

    @Override
    public boolean refund() {
        System.out.println("Processing PayPal refund.");
        return true;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
