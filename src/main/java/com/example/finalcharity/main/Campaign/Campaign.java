package com.example.finalcharity.main.Campaign;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.finalcharity.main.Donation.Donation;

import jakarta.persistence.Convert;
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
public class Campaign implements Iterable<Donation> {

    public Campaign() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private double goalAmount;
    private double currentAmount;

    @Convert(converter = CampaignStateConverter.class)
    private CampaignState state;

    public CampaignState getState() {
        return state;
    }

    public void setState(CampaignState state) {
        this.state = state;
    }

    @OneToMany(mappedBy = "campaign", fetch = FetchType.LAZY)
    private List<Donation> donations = new ArrayList<>();

    @JsonProperty("isActive")
    private boolean isActive;
    private boolean isGoalReached;

    public Campaign(Long id, String name, String description, double goalAmount, CampaignState state) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.goalAmount = goalAmount;
        this.currentAmount = 0;
        this.isActive = false;
        this.isGoalReached = false;
        this.donations = new ArrayList<Donation>();
        this.state = state;
    }

    public void next() {
        state.next(this);
    }

    public void prev() {
        state.prev(this);
    }

    public Long getID() {
        return id;
    }

    public boolean getStatus() {
        return isActive;
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

    @JsonProperty("isActive")
    public boolean isActive() {
        return isActive;
    }

    @JsonProperty("isActive")
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