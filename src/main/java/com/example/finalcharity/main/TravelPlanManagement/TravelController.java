package com.example.finalcharity.main.TravelPlanManagement;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/travel")
public class TravelController {
    private final TravelService travelService;

    @Autowired
    public TravelController(TravelService travelService) {
        this.travelService = travelService;
    }

    @PostMapping
    public ResponseEntity<TravelPlan> createTravelPlan(@RequestBody TravelPlan plan,
                                                      @RequestParam Long campaignId) {
        try {
            TravelPlan newPlan = travelService.createTravelPlan(plan, campaignId);
            return new ResponseEntity<>(newPlan, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<TravelPlan>> getAllTravelPlans() {
        return ResponseEntity.ok(travelService.getAllTravelPlans());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TravelPlan> getTravelPlan(@PathVariable Long id) {
        return travelService.getTravelPlanById(id)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TravelPlan> updateTravelPlan(@PathVariable Long id,
                                                      @RequestBody TravelPlan plan) {
        try {
            TravelPlan updated = travelService.updateTravelPlan(id, plan);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTravelPlan(@PathVariable Long id) {
        try {
            travelService.deleteTravelPlan(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/expenses")
    public ResponseEntity<TravelExpense> addExpense(@PathVariable Long id,
                                                   @RequestBody TravelExpense expense) {
        try {
            TravelExpense newExpense = travelService.addExpense(id, expense);
            return new ResponseEntity<>(newExpense, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}/expenses")
    public ResponseEntity<List<TravelExpense>> getTravelExpenses(@PathVariable Long id) {
        return ResponseEntity.ok(travelService.getTravelExpenses(id));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<TravelPlan> updateStatus(@PathVariable Long id,
                                                  @RequestBody TravelStatus status) {
        try {
            TravelPlan updated = travelService.updateStatus(id, status);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/participants")
    public ResponseEntity<TravelPlan> addParticipant(@PathVariable Long id,
                                                    @RequestParam Integer userId) {
        try {
            TravelPlan updated = travelService.addParticipant(id, userId);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}/expenses/total")
    public ResponseEntity<Map<String, Double>> getTotalExpenses(@PathVariable Long id) {
        double total = travelService.calculateTotalExpenses(id);
        return ResponseEntity.ok(Map.of("total", total));}
}