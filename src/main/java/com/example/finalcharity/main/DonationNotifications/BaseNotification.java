package com.example.finalcharity.main.DonationNotifications;

import com.example.finalcharity.main.Donation.Donation;
import com.example.finalcharity.main.PubSub.IObserver;

public class BaseNotification implements IObserver {

    @Override
    public void update(Donation donation) {
        System.out.println("Donation received: " + donation.getAmount() + " for campaign: " 
        + donation.getCampaignId());
    }
}
