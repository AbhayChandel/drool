package com.hexlindia.drool.video.dto;

public class VideoCommentDto {

    private UserRefDto userRefDto;
    private String datePosted;
    private String comment;
    private String likes;

    public VideoCommentDto(UserRefDto userRefDto, String datePosted, String comment, String likes) {
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

    public String getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }
}
