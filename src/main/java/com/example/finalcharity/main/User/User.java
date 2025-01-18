package com.example.finalcharity.main.User;

import java.util.List;

import com.example.finalcharity.main.Donation.Donation;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = NormalUser.class, name = "normal"),
        @JsonSubTypes.Type(value = CharityOrganizationUser.class, name = "charity"),
        @JsonSubTypes.Type(value = AdminUser.class, name = "admin")
})
public class User implements UserComponent {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", allocationSize = 1)
    private Integer userId;

    @Enumerated(EnumType.STRING)
    @NotNull
    protected UserType userType = UserType.NORMAL;

    private String name;
    private String email;
    private String phone;
    private String password;
    private boolean active;

    @OneToMany
    @JoinColumn(name = "donation_id")
    private List<Donation> donations;

    public User(Integer userId, UserType userType, String name, String email, String phone, String password) {
        this.userId = userId;
        this.userType = userType;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.active = true;
    }

    public User() {}

    // Getters and Setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public void displayDetails() {
        System.out.println("User: " + name + ", Email: " + email);
    }

    @Override
    public void add(UserComponent component) {
        throw new UnsupportedOperationException("Add operation not supported in User class");
    }

    @Override
    public void remove(UserComponent component) {
        throw new UnsupportedOperationException("Remove operation not supported in User class");
    }
}
