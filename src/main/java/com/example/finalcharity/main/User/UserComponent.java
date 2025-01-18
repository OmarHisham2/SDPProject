package com.example.finalcharity.main.User;


public interface UserComponent {
    void displayDetails();
    // Add any operations that should apply to both users and groups
    // For example:
    void add(UserComponent component);
    void remove(UserComponent component);

}
