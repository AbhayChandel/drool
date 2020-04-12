package com.hexlindia.drool.user.services;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AuthenticatedUserDetails implements Serializable {

    private final String accountId;
    private final String username;

    public AuthenticatedUserDetails(String accountId, String username) {
        this.accountId = accountId;
        this.username = username;
    }
}
