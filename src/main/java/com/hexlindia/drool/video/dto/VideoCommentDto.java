package com.hexlindia.drool.video.dto;

import java.time.LocalDateTime;

public class VideoCommentDto {

    private UserRefDto userRefDto;
    private LocalDateTime datePosted;
    private String comment;
    private int likes;

    public VideoCommentDto(UserRefDto userRefDto, LocalDateTime datePosted, String comment, int likes) {
        this.userRefDto = userRefDto;
        this.datePosted = datePosted;
        this.comment = comment;
        this.likes = likes;
    }

    public VideoCommentDto() {
    }

    public UserRefDto getUserRefDto() {
        return userRefDto;
    }

    public void setUserRefDto(UserRefDto userRefDto) {
        this.userRefDto = userRefDto;
    }

    public LocalDateTime getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(LocalDateTime datePosted) {
        this.datePosted = datePosted;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
