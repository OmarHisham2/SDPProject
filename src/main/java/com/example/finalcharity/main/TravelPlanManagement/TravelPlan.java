package com.example.finalcharity.main.TravelPlanManagement;

import com.example.finalcharity.main.Campaign.Campaign;
import com.example.finalcharity.main.User.User;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "travel_plans")
public class TravelPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String destination;
    private double budget;
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private Campaign relatedCampaign;
    
    @OneToMany(mappedBy = "travelPlan", cascade = CascadeType.ALL)
    private List<TravelExpense> expenses = new ArrayList<>();
    
    @Enumerated(EnumType.STRING)
    private TravelStatus status = TravelStatus.PLANNED;
    
    @ManyToMany
    @JoinTable(
        name = "travel_participants",
        joinColumns = @JoinColumn(name = "travel_plan_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> participants = new ArrayList<>();

}