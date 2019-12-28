package com.hexlindia.drool.discussion.to;

import java.io.Serializable;
import java.sql.Timestamp;

public class DiscussionReplyActivityTo implements Serializable {

    private Timestamp datePosted;
    private int likes;

    public Timestamp getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(Timestamp datePosted) {
        this.datePosted = datePosted;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
