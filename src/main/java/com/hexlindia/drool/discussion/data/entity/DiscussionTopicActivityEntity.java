package com.hexlindia.drool.discussion.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "discussion_topic_activity")
public class DiscussionTopicActivityEntity implements Serializable {

    @Id
    @Column(name = "discussion_topic_id")
    private Long discussionTopicId;

    private Timestamp datePosted;
    private Timestamp dateLastActive;
    private int views;
    private int likes;
    private int replies;

    public DiscussionTopicActivityEntity() {
    }

    public DiscussionTopicActivityEntity(Long discussionTopicId) {
        this.discussionTopicId = discussionTopicId;
    }

    public Long getDiscussionTopicId() {
        return this.discussionTopicId;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public Timestamp getDatePosted() {
        return datePosted;
    }

    public Timestamp getDateLastActive() {
        return dateLastActive;
    }

    public int getViews() {
        return views;
    }

    public int getLikes() {
        return likes;
    }

    public int getReplies() {
        return replies;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setDatePosted(Timestamp datePosted) {
        this.datePosted = datePosted;
    }

    public void setDateLastActive(Timestamp dateLastActive) {
        this.dateLastActive = dateLastActive;
    }

    public void setReplies(int replies) {
        this.replies = replies;
    }
}
