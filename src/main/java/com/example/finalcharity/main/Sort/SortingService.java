package com.example.finalcharity.main.Sort;


import java.util.List;

import com.example.finalcharity.main.Donation.Donation;


public class SortingService {
    private SortingStrategy selectedStrategy;

    public SortingService() {
        this.selectedStrategy = new NewestDonationsFirst(); // Default strategy
    }

    public SortingService(SortingStrategy selectedStrategy) {
        this.selectedStrategy = selectedStrategy;
    }

    public void sortDonations(List<Donation> donations) {
        this.selectedStrategy.sortDonations(donations);
    }
}