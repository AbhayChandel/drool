package com.hexlindia.drool.user.services;

import java.io.Serializable;

public class AuthenticatedUserDetails implements Serializable {

    private final Long userId;
    private final String username;

    public AuthenticatedUserDetails(Long userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }
}
