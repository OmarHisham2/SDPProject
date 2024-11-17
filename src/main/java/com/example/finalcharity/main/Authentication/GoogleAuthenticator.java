package com.example.finalcharity.main.Authentication;



public class GoogleAuthenticator implements AuthenticationProvider {

    @Override
    public boolean login(String email, String password) {
        // Implement Google login logic here
        return true; // Placeholder
    }

    @Override
    public boolean register(String email, String password) {
        // Implement Google registration logic here
        return true; // Placeholder
    }
}
