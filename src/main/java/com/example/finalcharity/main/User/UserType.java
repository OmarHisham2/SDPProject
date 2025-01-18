package com.example.finalcharity.main.User;

public enum UserType {
    NORMAL(1, "USER"),
    CHARITY(2, "CHARITY"),
    ADMIN(3, "ADMIN");

    private final int value;
    private final String role;

    UserType(int value, String role) {
        this.value = value;
        this.role = role;
    }

    public int getValue() {
        return value;
    }

    public String getRole() {
        return role;
    }

    public static UserType fromValue(int value) {
        for (UserType type : UserType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid UserType value: " + value);
    }
}