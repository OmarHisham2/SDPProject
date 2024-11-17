package com.example.finalcharity.main.User;


import java.util.Map;

public interface RegistrationStrategy {
    void register(Map<String, String> userData);
}