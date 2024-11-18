package com.example.finalcharity.main.Campaign;

import java.util.List;

import com.example.finalcharity.main.Donation.Donation;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;

@Entity
@Table(name = "campaigns")
public class Campaign {

    public Campaign() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private double goalAmount;
    private double currentAmount;

    @OneToMany(mappedBy = "userId", fetch = FetchType.LAZY)
    private List<Donation> donations;

    private boolean isActive;
    private boolean isGoalReached;

    public Campaign(Long id, String name, String description, double goalAmount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.goalAmount = goalAmount;
        this.currentAmount = 0;
        this.isActive = false;
        this.isGoalReached = false;
        this.donations = new ArrayList<Donation>();
    }

    public Long getID() {
        return id;
    }

    public void setID(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getGoalAmount() {
        return goalAmount;
    }

    public void setGoalAmount(double goalAmount) {
        this.goalAmount = goalAmount;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(double currentAmount) {
        this.currentAmount = currentAmount;
    }

    public List<Donation> getDonations() {
        return donations;
    }

    public void setDonations(List<Donation> donations) {
        this.donations = donations;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isGoalReached() {
        return isGoalReached;
    }

    public void setGoalReached(boolean isGoalReached) {
        this.isGoalReached = isGoalReached;
    }

}
