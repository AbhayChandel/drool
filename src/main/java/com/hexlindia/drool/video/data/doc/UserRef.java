package com.hexlindia.drool.video.data.doc;

public class UserRef {

    private final String id;
    private final String username;

    public UserRef(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
