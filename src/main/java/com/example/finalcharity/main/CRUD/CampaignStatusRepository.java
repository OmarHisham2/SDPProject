package com.example.finalcharity.main.CRUD;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignStatusRepository extends JpaRepository<CampaignStatus, Integer> {
    List<CampaignStatus> findByIsActiveTrue();
}