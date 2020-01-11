package com.hexlindia.drool.user.services;

import java.io.Serializable;

public class AuthenticatedUserDetails implements Serializable {

    private final String userId;
    private final String username;

    public AuthenticatedUserDetails(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }
}
