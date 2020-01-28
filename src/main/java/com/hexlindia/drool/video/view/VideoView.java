package com.hexlindia.drool.video.view;

public class VideoView {

    private String videoId;
    private String title;
    private String userId;
    private String sourceVideoId;
    private String datePosted;
    private String views;
    private String likes;
    private String description;
    private String commentCount;

    public VideoView(String videoId, String title, String userId, String sourceVideoId, String datePosted, String views, String likes, String description, String commentCount) {
        this.videoId = videoId;
        this.title = title;
        this.userId = userId;
        this.sourceVideoId = sourceVideoId;
        this.datePosted = datePosted;
        this.views = views;
        this.likes = likes;
        this.description = description;
        this.commentCount = commentCount;
    }

    public VideoView() {
    }

    public String getVideoId() {
        return videoId;
    }

    public String getTitle() {
        return title;
    }

    public String getUserId() {
        return userId;
    }

    public String getSourceVideoId() {
        return sourceVideoId;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public String getViews() {
        return views;
    }

    public String getLikes() {
        return likes;
    }

    public String getDescription() {
        return description;
    }

    public String getCommentCount() {
        return commentCount;
    }
}
