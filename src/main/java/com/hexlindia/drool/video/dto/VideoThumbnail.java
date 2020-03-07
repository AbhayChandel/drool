package com.hexlindia.drool.video.dto;

public class VideoThumbnail {

    private String id;
    private String sourceId;
    private String title;
    private Integer views;
    private Integer likes;

    public VideoThumbnail(String id, String sourceId, String title, Integer views, Integer likes) {
        this.id = id;
        this.sourceId = sourceId;
        this.title = title;
        this.views = views;
        this.likes = likes;
    }

    public String getId() {
        return id;
    }

    public String getSourceId() {
        return sourceId;
    }

    public String getTitle() {
        return title;
    }

    public Integer getViews() {
        return views;
    }

    public Integer getLikes() {
        return likes;
    }
}
