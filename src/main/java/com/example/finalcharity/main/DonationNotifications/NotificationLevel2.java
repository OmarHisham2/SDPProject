package com.example.finalcharity.main.DonationNotifications;

import com.example.finalcharity.main.Donation.Donation;
import com.example.finalcharity.main.PubSub.IObserver;

public class NotificationLevel2 extends NotificationDecorator {
    private final IObserver wrappedObserver;

    public NotificationLevel2(IObserver wrappedObserver) {
        this.wrappedObserver = wrappedObserver;
    }

    @Override
    public void update(Donation donation) {
        wrappedObserver.update(donation); // Call the previous decorator (log + email)

        // Add SMS notification behavior
        System.out.println("Sending SMS notification: Donation of " + donation.getAmount() + " received for campaign: "
                + donation.getCampaignId());
    }
}
