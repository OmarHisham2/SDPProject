package com.example.finalcharity.main.facade.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class DonationRequest {
    private Long campaignId;

    @NotNull
    @Min(1)
    private double amount;

    private String message;

    @NotNull
    private Integer userId;

    private String currency = "USD"; // default value

    @NotNull
    private int paymentMethod; // 1 for credit card, 2 for PayPal

    // Credit Card fields
    private String cardNumber;
    private String cardHolder;
    private String expiryDate;
    private String cvv;

    // PayPal fields
    private String email;
    private String password;

    // Default constructor
    public DonationRequest() {}

    // Full constructor
    public DonationRequest(Long campaignId, double amount, String message, Integer userId,
                           String currency, int paymentMethod, String cardNumber,
                           String cardHolder, String expiryDate, String cvv,
                           String email, String password) {
        this.campaignId = campaignId;
        this.amount = amount;
        this.message = message;
        this.userId = userId;
        this.currency = currency;
        this.paymentMethod = paymentMethod;
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(int paymentMethod) {
        this.paymentMethod = paymentMethod;
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

    // toString method for debugging
    @Override
    public String toString() {
        return "DonationRequest{" +
                "campaignId=" + campaignId +
                ", amount=" + amount +
                ", message='" + message + '\'' +
                ", userId=" + userId +
                ", currency='" + currency + '\'' +
                ", paymentMethod=" + paymentMethod +
                ", cardNumber='" + cardNumber + '\'' +
                ", cardHolder='" + cardHolder + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                ", cvv='" + cvv + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}