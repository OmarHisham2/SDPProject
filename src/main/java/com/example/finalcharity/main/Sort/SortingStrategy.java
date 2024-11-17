package com.example.finalcharity.main.Sort;

import java.util.List;

import com.example.finalcharity.main.Donation.Donation;

public interface SortingStrategy {

    public void sortDonations(List<Donation> donations);

}
