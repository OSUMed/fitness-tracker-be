package com.srikanth.fitnesstrackerbe.domain;

public class AccountDetails {
    private final String username;
    private final Integer userId;

    public AccountDetails(String username, Integer userId) {
        this.username = username;
        this.userId = userId;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public Integer getUserId() {
        return userId;
    }
}