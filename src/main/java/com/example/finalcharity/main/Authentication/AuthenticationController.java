package com.example.finalcharity.main.Authentication;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final ContextAuthenticator contextAuthenticator;

    @Autowired
    public AuthenticationController(ContextAuthenticator contextAuthenticator) {
        this.contextAuthenticator = contextAuthenticator;
    }

    @PostMapping("/login")
    public boolean login(@RequestParam String email, @RequestParam String password,@RequestParam Long method) {
        return contextAuthenticator.login(email, password,method);
    }

    @PostMapping("/register")
    public boolean register(@RequestParam String email, @RequestParam String password,@RequestParam Long method) {
        return contextAuthenticator.register(email, password,method);
    }
}
