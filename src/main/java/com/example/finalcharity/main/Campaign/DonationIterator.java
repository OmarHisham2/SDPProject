package com.example.finalcharity.main.Campaign;

import com.example.finalcharity.main.Donation.Donation;

public interface DonationIterator {
    boolean hasNext();
    Donation next();
    void reset();
}