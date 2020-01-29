package com.hexlindia.drool.common.view;

public class UserProfileCardView {

    private String userId;
    private String username;

    public UserProfileCardView(String userId, String username) {
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
