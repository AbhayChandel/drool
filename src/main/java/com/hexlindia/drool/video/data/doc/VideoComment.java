package com.hexlindia.drool.video.data.doc;

import com.hexlindia.drool.common.data.doc.UserRef;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

public class VideoComment {

    private String id;
    private UserRef userRef;
    private LocalDateTime datePosted;
    private String comment;
    private int likes;

    public VideoComment(UserRef userRef, LocalDateTime datePosted, String comment) {
        this.userRef = userRef;
        this.datePosted = datePosted;
        this.comment = comment;
        this.id = ObjectId.get().toString();
    }

    public VideoComment() {
        this.id = ObjectId.get().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserRef getUserRef() {
        return userRef;
    }

    public void setUserRef(UserRef userRef) {
        this.userRef = userRef;
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
