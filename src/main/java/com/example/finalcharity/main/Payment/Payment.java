package com.example.finalcharity.main.Payment;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Payment {

@Id
    @SequenceGenerator(
            name = "payment_sequence",
            sequenceName = "payment_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "payment_sequence"
    )
    private Long paymentId;

    @NotNull
    private Long donationId;


    @NotNull
    private double amount;  // Payment amount field
    private String status;  // Payment status field

    @Convert(converter = PaymentMethodConverter.class)
    private PaymentMethod paymentStrategy;

    public Payment() {}

    public Payment(Long donationId, PaymentMethod paymentStrategy, double amount) {
        this.donationId = donationId;
        this.paymentStrategy = paymentStrategy;
        this.amount = amount;
        this.status = "FINISHED";  // Default status
    }

    // Getters and Setters
    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Long getDonationId() {
        return donationId;
    }

    public void setDonationId(Long donationId) {
        this.donationId = donationId;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentStrategy;
    }

    public void setPaymentMethod(PaymentMethod paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public double getAmount() {
        return amount;  // Getter for amount
    }

    public void setAmount(double amount) {
        this.amount = amount;  // Setter for amount
    }

    public String getStatus() {
        return status;  // Getter for status
    }

    public void setStatus(String status) {
        this.status = status;  // Setter for status
    }

    // Set payment strategy for specific payment behavior (Strategy pattern)
    public void setStrategy(PaymentMethod strategy) {
        this.paymentStrategy = strategy;
    }


    // Initiates payment using the set strategy
    public boolean pay() {
        return true;
    }

    // Validates payment details
    public boolean validatePaymentDetails() {
        return true;
    }

    // Refunds the payment
    public boolean refund() {
        return true;
    }
}
