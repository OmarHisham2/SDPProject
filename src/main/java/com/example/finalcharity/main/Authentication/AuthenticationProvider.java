package com.example.finalcharity.main.Authentication;


public interface AuthenticationProvider {

    boolean login(String email, String password);
    boolean register(String email, String password);
}
