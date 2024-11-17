package com.example.finalcharity.main.Donation;

import java.util.Map;

import com.example.finalcharity.main.Campaign.Campaign;
import com.example.finalcharity.main.Donation.Donation;
import com.example.finalcharity.main.Payment.Payment;
import com.example.finalcharity.main.User.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;
import java.util.HashMap;

@Entity
@Table(name = "donations")
public class Donation {

    public Donation() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long donationID;

    @NotNull
    @Min(1)
    private double amount;
    private String status;

    private String message;
    private Date processedDate;
    private String currency = "USD";

@ManyToOne
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    public Donation(double amount, Long userId, String message, Campaign campaignId, Date processedDate) {
        this.amount = amount;
        this.message = message;
        this.campaign = campaignId;
        this.processedDate = processedDate;
    }

    public boolean refund() {
        // Logic to refund donation
        return true;
    }

    public boolean processDonation(Payment payment) {
        // Logic to process donation
        return true;
    }

    public Map<String, Object> getDonationDetail() {
        Map<String, Object> details = new HashMap<>();
        details.put("donationID", donationID);
        details.put("amount", amount);
        details.put("status", status);
        details.put("message", message);
        details.put("processedDate", processedDate);
        details.put("currency", currency);
        details.put("campaignId", campaign.getID());
        return details;
    }

    public boolean markAsProcessed() {
        // Logic to mark donation as processed
        return true;
    }

    
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


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getProcessedDate() {
        return processedDate;
    }

    public void setProcessedDate(Date processedDate) {
        this.processedDate = processedDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Donation{" +
                "amount=" + amount +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", processedDate=" + processedDate +
                ", currency='" + currency + '\'' +
                '}';
    }

}