package com.hexlindia.drool.user.data.doc;

public class VideoLike {

    private String videoId;
    private String title;

    public VideoLike(String videoId, String title) {
        this.videoId = videoId;
        this.title = title;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getTitle() {
        return title;
    }
}
