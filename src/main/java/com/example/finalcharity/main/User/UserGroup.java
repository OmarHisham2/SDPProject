package com.example.finalcharity.main.User;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_groups")
public class UserGroup implements UserComponent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String groupName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_group_members",
            joinColumns = @JoinColumn(name = "user_group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> members = new ArrayList<>();  // Changed to List<User>

    public UserGroup() {
    }

    public UserGroup(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public void add(UserComponent component) {
        if (component instanceof User) {
            members.add((User) component);  // Now can add User
        } else {
            throw new UnsupportedOperationException("Only User instances can be added");
        }
    }

    @Override
    public void remove(UserComponent component) {
        if (component instanceof User) {
            members.remove(component);  // Now can remove User
        } else {
            throw new UnsupportedOperationException("Only User instances can be removed");
        }
    }

    @Override
    public void displayDetails() {
        System.out.println("Group: " + groupName);
        for (User member : members) {
            member.displayDetails();  // Will call displayDetails for User
        }
    }

    // Getters and Setters
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public Long getId() {
        return id;
    }
}
