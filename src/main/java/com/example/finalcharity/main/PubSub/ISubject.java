package com.example.finalcharity.main.PubSub;

import java.util.ArrayList;

import com.example.finalcharity.main.Donation.Donation;

public interface ISubject {

    public void addObserver(IObserver observer);

    public void removeObserver(IObserver observer);

    public void notifyObservers(Donation donation);

}
