package com.hexlindia.drool.common.data.doc;

import org.bson.types.ObjectId;

public class UserRef {

    private ObjectId id;
    private String username;

    public UserRef(ObjectId id, String username) {
        this.id = id;
        this.username = username;
    }

    public UserRef() {
    }

    public ObjectId getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
