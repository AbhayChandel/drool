package com.hexlindia.drool.video.data.doc;

import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;

public class VideoComment {

    private final UserDetails userDetails;
    private final LocalDateTime datePosted;
    private final String comment;
    private int likes;

    public VideoComment(UserDetails userDetails, LocalDateTime datePosted, String comment) {
        this.userDetails = userDetails;
        this.datePosted = datePosted;
        this.comment = comment;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public LocalDateTime getDatePosted() {
        return datePosted;
    }

    public String getComment() {
        return comment;
    }

    public int getLikes() {
        return likes;
    }
}
