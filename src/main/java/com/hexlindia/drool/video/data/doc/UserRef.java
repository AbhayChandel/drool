package com.hexlindia.drool.video.data.doc;

public class UserRef {

    private String id;
    private String username;

    public UserRef(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public UserRef() {
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
