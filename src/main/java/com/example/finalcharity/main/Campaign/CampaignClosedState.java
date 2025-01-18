package com.example.finalcharity.main.Campaign;

public class CampaignClosedState implements CampaignState {

    public void next(Campaign campaign) {
        System.out.println("Cannot publish a closed campaign.");
    }

    public void prev(Campaign campaign) {
        System.out.println("Campaign is already closed.");
    }
}
