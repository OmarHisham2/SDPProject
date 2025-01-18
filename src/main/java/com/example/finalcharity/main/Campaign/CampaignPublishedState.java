package com.example.finalcharity.main.Campaign;


public class CampaignPublishedState implements CampaignState {

    public void next(Campaign campaign) {
        campaign.setState(new CampaignClosedState());
    }

    public void prev(Campaign campaign) {
        campaign.setState(new CampaignClosedState());
        System.out.println("cannot draft a published campaign.");
    }
}