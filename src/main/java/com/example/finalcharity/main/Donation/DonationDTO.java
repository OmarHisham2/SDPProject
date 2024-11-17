package com.example.finalcharity.main.Donation;

public class DonationDTO {
    
    private Long donationID;
    private double amount;
    private String status;
    private Long userId; // User ID
    private String message;
    private String currency;
    private Long campaignId; // Campaign ID


    public Long getDonationID() {
        return donationID;
    }
    public void setDonationID(Long donationID) {
        this.donationID = donationID;
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
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public Long getCampaignId() {
        return campaignId;
    }
    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }


}