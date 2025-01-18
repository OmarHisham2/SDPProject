package com.example.finalcharity.main.facade.dto;

public class CampaignStatistics {
    private int totalDonations;
    private double totalAmount;
    private double averageDonation;
    private double completionPercentage;
    private boolean isActive;

    // Default constructor
    public CampaignStatistics() {}

    // Getters and Setters
    public int getTotalDonations() {
        return totalDonations;
    }

    public void setTotalDonations(int totalDonations) {
        this.totalDonations = totalDonations;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getAverageDonation() {
        return averageDonation;
    }

    public void setAverageDonation(double averageDonation) {
        this.averageDonation = averageDonation;
    }

    public double getCompletionPercentage() {
        return completionPercentage;
    }

    public void setCompletionPercentage(double completionPercentage) {
        this.completionPercentage = completionPercentage;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}