package com.example.finalcharity.main.User.factory;

import com.example.finalcharity.main.User.CharityOrganizationUser;
import com.example.finalcharity.main.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CharityUserFactory implements UserFactory {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CharityUserFactory(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(User baseUser) {
        CharityOrganizationUser charityUser = new CharityOrganizationUser();
        charityUser.setName(baseUser.getName());
        charityUser.setEmail(baseUser.getEmail());
        charityUser.setPassword(passwordEncoder.encode(baseUser.getPassword())); // Encrypt password
        charityUser.setPhone(baseUser.getPhone());
        charityUser.setActive(true);
        charityUser.setUserType(0);
        return charityUser;
    }
}