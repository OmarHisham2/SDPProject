package com.example.finalcharity.main.DonationNotifications;

import com.example.finalcharity.main.Donation.Donation;
import com.example.finalcharity.main.PubSub.IObserver;

public class NotificationLevel1 extends NotificationDecorator {
    private final IObserver wrappedObserver;

    public NotificationLevel1(IObserver wrappedObserver) {
        this.wrappedObserver = wrappedObserver;
    }

    @Override
    public void update(Donation donation) {
        wrappedObserver.update(donation); // Call the base behavior (log)
        
        // Add email notification behavior
        System.out.println("Sending email notification: Donation of " 
        +
         donation.getAmount() + " received for campaign: " + 
         donation.getCampaignId());
    }
}
