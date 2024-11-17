package com.example.finalcharity.main.User;


import com.example.finalcharity.main.Campaign.Campaign;
import com.example.finalcharity.main.Donation.Donation;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Map;

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
public abstract class User {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    protected Long userId;

    @NotNull
    protected String name;

    @NotNull
    protected String email;

    @NotNull
    protected String phone;

    @NotNull
    protected boolean active = true;

    @Transient
    protected RegistrationContext registrationContext;

@OneToMany
    @JoinColumn(name = "donation_id")
    private List<Donation> donations;

    // @OneToMany(mappedBy = "user")
    // private List<Campaign> campaigns;


    public User(Long userId, String name, String email, String phone) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    // No-argument constructor required by JPA
    public User() {}

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
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

    public boolean isActive() { return active; }

    public void setActive(boolean active)
    { this.active = active;
    //return this;
    }

    public abstract void register(Map<String, String> userData);
}

