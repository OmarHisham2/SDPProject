package com.example.finalcharity.main.CRUD;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "campaign_statuses")
public class CampaignStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private String name;        // "Draft", "Active", "Closed"
    
    private String description;
    
    @Column(nullable = false)
    private boolean isActive = true;
}