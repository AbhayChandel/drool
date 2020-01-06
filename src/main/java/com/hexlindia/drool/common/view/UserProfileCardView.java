package com.hexlindia.drool.common.view;

public class UserProfileCardView {

    private Long userId;
    private String username;

    public UserProfileCardView(Long userId, String username) {
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
