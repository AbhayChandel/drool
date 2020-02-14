package com.hexlindia.drool.video.dto;

public class UserRefDto {

    private String id;
    private String username;

    public UserRefDto(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public UserRefDto() {
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
