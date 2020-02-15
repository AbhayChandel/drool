package com.hexlindia.drool.video.data.doc;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "videos")
public class VideoDoc {

    @Id
    private String id;
    private String type;
    private boolean active;
    private String title;
    private String description;
    private String sourceId;
    private LocalDateTime datePosted;
    private int likes;
    private int views;
    private List<ProductRef> productRefList;
    private UserRef userRef;
    private List<VideoComment> videoCommentList;

    public VideoDoc(String type, String title, String description, String sourceId, List<ProductRef> productRefList, UserRef userRef) {
        this.type = type;
        this.title = title;
        this.description = description;
        this.sourceId = sourceId;
        this.productRefList = productRefList;
        this.userRef = userRef;
    }

    public VideoDoc() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public LocalDateTime getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(LocalDateTime datePosted) {
        this.datePosted = datePosted;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public List<ProductRef> getProductRefList() {
        return productRefList;
    }

    public void setProductRefList(List<ProductRef> productRefList) {
        this.productRefList = productRefList;
    }

    public UserRef getUserRef() {
        return userRef;
    }

    public void setUserRef(UserRef userRef) {
        this.userRef = userRef;
    }

    public List<VideoComment> getVideoCommentList() {
        return videoCommentList;
    }

    public void setVideoCommentList(List<VideoComment> videoCommentList) {
        this.videoCommentList = videoCommentList;
    }
}
