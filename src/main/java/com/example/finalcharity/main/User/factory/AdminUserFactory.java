package com.example.finalcharity.main.User.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.finalcharity.main.User.AdminUser;
import com.example.finalcharity.main.User.User;
import com.example.finalcharity.main.User.UserType;

@Component
public class AdminUserFactory implements UserFactory {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminUserFactory(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(User baseUser) {
        // Use singleton getInstance method instead of constructor
        AdminUser admin = AdminUser.getInstance(
            0, 
            baseUser.getName(),
            baseUser.getEmail(),
            baseUser.getPhone(),
            passwordEncoder.encode(baseUser.getPassword()),
            2
        );

        // Set additional properties if needed
        admin.setActive(true);
        admin.setUserType(UserType.ADMIN.getValue());

        return admin;
    }
}