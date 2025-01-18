package com.example.finalcharity.main.User.factory;

import com.example.finalcharity.main.User.NormalUser;
import com.example.finalcharity.main.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class NormalUserFactory implements UserFactory {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public NormalUserFactory(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(User baseUser) {
        NormalUser normalUser = new NormalUser();
        normalUser.setName(baseUser.getName());
        normalUser.setEmail(baseUser.getEmail());
        normalUser.setPassword(passwordEncoder.encode(baseUser.getPassword()));
        normalUser.setPhone(baseUser.getPhone());
        normalUser.setActive(true);
        normalUser.setUserType(1);
        return normalUser;
    }
}