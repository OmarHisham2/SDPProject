package com.example.finalcharity.main.User;


import java.util.Map;
import jakarta.persistence.*;

@Entity
@Table(name = "admin_users")  // Changed table name to 'admin_users'
public class AdminUser extends User {
    private int level;

    public AdminUser(int userId, String name, String email, String phone, int level) {
        super(userId, name, email, phone);
        this.level = level;
    }

    // No-argument constructor required by JPA
    public AdminUser() {
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    @Override
    public void register(Map<String, String> userData) {
        // Registration logic for AdminUser
    }
}
