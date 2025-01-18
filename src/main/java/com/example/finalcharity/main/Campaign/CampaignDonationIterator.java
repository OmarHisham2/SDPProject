package com.example.finalcharity.main.Campaign;

import java.util.List;

import com.example.finalcharity.main.Donation.Donation;

public class CampaignDonationIterator implements DonationIterator {
    private List<Donation> donations;
    private int position;

    public CampaignDonationIterator(List<Donation> donations) {
        this.donations = donations;
        this.position = 0;
    }

    @Override
    public boolean hasNext() {
        return position < donations.size();
    }

    @Override
    public Donation next() {
        if (this.hasNext()) {
            return donations.get(position++);
        }
        return null;
    }

    @Override
    public void reset() {
        position = 0;
    }
}