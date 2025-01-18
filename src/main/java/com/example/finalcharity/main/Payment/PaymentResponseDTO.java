package com.example.finalcharity.main.Payment;

public class PaymentResponseDTO {
    private Long paymentId;
    private Long donationId;
    private double amount;
    private String status;

    public PaymentResponseDTO(){}

    public PaymentResponseDTO(Long paymentId, Long donationId, double amount, String status) {
        this.paymentId = paymentId;
        this.donationId = donationId;
        this.amount = amount;
        this.status = status;
    }


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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}