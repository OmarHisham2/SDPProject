package com.example.finalcharity.main.Campaign;

public class CampaignDraftState implements CampaignState {

    public void next(Campaign campaign) {
        campaign.setState(new CampaignPublishedState());
        System.out.println("Campaign published.");
    }

    public void prev(Campaign campaign) {
        System.out.println("Cannot close a draft campaign.");
    }
}
