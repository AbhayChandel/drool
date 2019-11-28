package com.hexlindia.drool.usermanagement.business.api.to;

import javax.validation.constraints.NotEmpty;

public class UserAccountTo {

    @NotEmpty(message = "Email cannot be empty")
    private String email;

    private long id;

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
