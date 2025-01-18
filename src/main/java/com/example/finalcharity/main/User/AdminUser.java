package com.example.finalcharity.main.User;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "admin_users")
public class AdminUser extends User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Transient // Non-persistent singleton instance
    private static volatile AdminUser instance;
    private static final Object LOCK = new Object();

    private int level;

    // Protected no-args constructor for JPA
    protected AdminUser() {
        super();
    }

    // Private constructor matching getInstance parameters
    private AdminUser(int userId, String name, String email, String phone, String password, int level) {
        super(userId, UserType.ADMIN, name, email, phone, password);
        this.level = level;
        setActive(true);
    }

    // Singleton accessor
    public static AdminUser getInstance(int userId, String name, String email,
                                        String phone, String password, int level) {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = new AdminUser(userId, name, email, phone, password, level);
                }
            }
        }
        return instance;
    }

    @PrePersist
    protected void onCreate() {
        if (getUserType() != UserType.ADMIN) {
            throw new IllegalStateException("Invalid user type for AdminUser");
        }
    }

    // Handle deserialization
    protected Object readResolve() {
        return instance;
    }

    // Getters and Setters
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
