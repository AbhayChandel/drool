package com.hexlindia.drool.video.dto;

import com.hexlindia.drool.common.data.doc.UserRef;

public class VideoThumbnail {

    private String id;
    private String sourceId;
    private String title;
    private Integer views;
    private Integer likes;
    private UserRef userRef;

    public VideoThumbnail(String id, String sourceId, String title, Integer views, Integer likes, UserRef userRef) {
        this.id = id;
        this.sourceId = sourceId;
        this.title = title;
        this.views = views;
        this.likes = likes;
        this.userRef = userRef;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public UserRef getUserRef() {
        return userRef;
    }

    public void setUserRef(UserRef userRef) {
        this.userRef = userRef;
    }
}
