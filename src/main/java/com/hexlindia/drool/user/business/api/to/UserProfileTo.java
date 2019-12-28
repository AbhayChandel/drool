package com.hexlindia.drool.user.business.api.to;

import javax.validation.constraints.Positive;

public class UserProfileTo {

    @Positive(message = "Not a valid Id")
    private long id;
    private String username;
    private long mobile;
    private String city;
    private char gender;

    public UserProfileTo(long id, String username, long mobile, String city, char gender) {
        this.id = id;
        this.username = username;
        this.mobile = mobile;
        this.city = city;
        this.gender = gender;
    }

    public UserProfileTo() {
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public long getMobile() {
        return mobile;
    }

    public String getCity() {
        return city;
    }

    public char getGender() {
        return gender;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setMobile(long mobile) {
        this.mobile = mobile;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }
}


