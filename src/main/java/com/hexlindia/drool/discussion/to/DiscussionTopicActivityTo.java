package com.hexlindia.drool.discussion.to;

import java.io.Serializable;
import java.sql.Timestamp;

public class DiscussionTopicActivityTo implements Serializable {

    private Timestamp datePosted;
    private Timestamp dateLastActive;
    private int views;
    private int likes;
    private int replies;


    public Timestamp getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(Timestamp datePosted) {
        this.datePosted = datePosted;
    }

    public Timestamp getDateLastActive() {
        return dateLastActive;
    }

    public void setDateLastActive(Timestamp dateLastActive) {
        this.dateLastActive = dateLastActive;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getReplies() {
        return replies;
    }

    public void setReplies(int replies) {
        this.replies = replies;
    }
}
