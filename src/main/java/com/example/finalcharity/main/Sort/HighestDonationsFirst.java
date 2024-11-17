package com.example.finalcharity.main.Sort;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.example.finalcharity.main.Donation.Donation;


public class HighestDonationsFirst implements SortingStrategy {

    @Override
    public void sortDonations(List<Donation> donations) {
        Collections.sort(donations, Comparator.comparing(Donation::getAmount).reversed());
    }

}