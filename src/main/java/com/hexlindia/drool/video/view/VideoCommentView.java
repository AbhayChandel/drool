package com.hexlindia.drool.video.view;

public class VideoCommentView {
    private String videoCommentId;
    private String videoId;
    private String comment;
    private String userId;
    private String datePosted;
    private String likes;

    public VideoCommentView(String videoCommentId, String videoId, String comment, String userId, String datePosted, String likes) {
        this.videoCommentId = videoCommentId;
        this.videoId = videoId;
        this.comment = comment;
        this.userId = userId;
        this.datePosted = datePosted;
        this.likes = likes;
    }

    public String getVideoCommentId() {
        return videoCommentId;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getComment() {
        return comment;
    }

    public String getUserId() {
        return userId;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public String getLikes() {
        return likes;
    }
}
