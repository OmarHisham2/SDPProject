package com.example.finalcharity.main.User.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserFactoryProvider {
    private final Map<Integer, UserFactory> factories;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserFactoryProvider(AdminUserFactory adminFactory,
                               NormalUserFactory normalFactory,
                               CharityUserFactory charityFactory,
                               BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        factories = Map.of(
                2, adminFactory,
                1, normalFactory,
                0, charityFactory
        );
    }

    public UserFactory getFactory(Integer userType) {
        UserFactory factory = factories.get(userType);
        if (factory == null) {
            throw new IllegalArgumentException("Invalid user type: " + userType);
        }
        return factory;
    }
}
