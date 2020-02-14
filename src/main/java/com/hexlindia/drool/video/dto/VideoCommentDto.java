package com.hexlindia.drool.video.dto;

import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;

public class VideoCommentDto {

    private UserDetails userDetails;
    private LocalDateTime datePosted;
    private String comment;
    private int likes;

    public VideoCommentDto(UserDetails userDetails, LocalDateTime datePosted, String comment, int likes) {
        this.userDetails = userDetails;
        this.datePosted = datePosted;
        this.comment = comment;
        this.likes = likes;
    }

    public VideoCommentDto() {
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

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public void setDatePosted(LocalDateTime datePosted) {
        this.datePosted = datePosted;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
