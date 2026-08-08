package com.example.movieapi.model.enums;


public enum RoleType {
    USER("USER"),
    ADMIN("ADMIN");

    private final String name;

    RoleType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}