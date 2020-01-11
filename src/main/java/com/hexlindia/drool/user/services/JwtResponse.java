package com.hexlindia.drool.user.services;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;

    @JsonProperty("authToken")
    private final String authToken;

    @JsonProperty("userDetails")
    private final AuthenticatedUserDetails authenticatedUserDetails;

    public JwtResponse(String authToken, AuthenticatedUserDetails authenticatedUserDetails) {
        this.authToken = authToken;
        this.authenticatedUserDetails = authenticatedUserDetails;
    }

    public String getAuthToken() {
        return authToken;
    }

    public AuthenticatedUserDetails getAuthenticatedUserDetails() {
        return authenticatedUserDetails;
    }
}
