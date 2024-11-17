package com.example.finalcharity.main.PubSub;

import com.example.finalcharity.main.Donation.Donation;

public interface IObserver {
    public void update(Donation donation);
}
