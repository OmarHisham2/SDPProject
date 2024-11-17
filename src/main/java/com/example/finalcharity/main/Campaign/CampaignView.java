package com.example.finalcharity.main.Campaign;


public class CampaignView {
    // Campaign details
    private String campaignName;
    private String campaignDescription;
    private double campaignGoalAmount;
    private double currentAmount;

    public CampaignView(String campaignName, String campaignDescription, double campaignGoalAmount) {
        this.campaignName = campaignName;
        this.campaignDescription = campaignDescription;
        this.campaignGoalAmount = campaignGoalAmount;
        this.currentAmount = 0.0; // initial amount
    }

    public void displayCampaignDetails(String campaignName, String campaignDescription, double campaignGoalAmount,
            double currentAmount) {
        System.out.println("Campaign Name: " + campaignName);
        System.out.println("Description: " + campaignDescription);
        System.out.println("Goal Amount: " + campaignGoalAmount);
        System.out.println("Current Amount: " + currentAmount);
    }

    public void setCurrentAmount(double amount) {
        this.currentAmount = amount;
    }
}
