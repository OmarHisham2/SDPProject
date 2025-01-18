package com.example.finalcharity.main.facade.dto;

import com.example.finalcharity.main.Campaign.Campaign;

public class CampaignCreationRequest {
    private Campaign campaign;
    private DonationRequest initialDonation;

    // Default constructor
    public CampaignCreationRequest() {}

    // Getters and setters
    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public DonationRequest getInitialDonation() {
        return initialDonation;
    }

    public void setInitialDonation(DonationRequest initialDonation) {
        this.initialDonation = initialDonation;
    }
}