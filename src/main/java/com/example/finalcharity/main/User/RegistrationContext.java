package com.example.finalcharity.main.User;


import jakarta.persistence.Embeddable;
import java.util.Map;

@Embeddable
public class RegistrationContext {
    private RegistrationStrategy strategy;

    public void setStrategy(RegistrationStrategy strategy) {
        this.strategy = strategy;
    }

    public void register(Map<String, String> userData) {
        strategy.register(userData);
}
    }