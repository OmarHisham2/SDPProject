package com.example.finalcharity.main.Payment;

public class PaypalPayment implements PaymentMethod {
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

    // Getters and Setters
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