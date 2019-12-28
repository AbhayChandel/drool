package com.hexlindia.drool.user.business.api.to;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UserRegistrationDetailsTo {

    @NotEmpty(message = "Username cannot be empty")
    private final String username;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email is not correct")
    private final String email;

    @NotEmpty(message = "Password cannot be empty")
    private String password;

    public UserRegistrationDetailsTo(String username, String email, String password) {

        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
