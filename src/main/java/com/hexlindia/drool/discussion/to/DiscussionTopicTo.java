package com.hexlindia.drool.discussion.to;

import com.hexlindia.drool.discussion.to.validation.DiscussionTopicCreateValidation;
import com.hexlindia.drool.discussion.to.validation.DiscussionTopicUpdateValidation;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

public class DiscussionTopicTo {

    @NotNull(message = "Discussion Id cannot be null", groups = {DiscussionTopicUpdateValidation.class})
    private Long id;

    @NotEmpty(message = "Topic title cannot be empty", groups = {DiscussionTopicCreateValidation.class, DiscussionTopicUpdateValidation.class})
    private String topic;

    @NotNull(message = "User Id cannot be null", groups = {DiscussionTopicCreateValidation.class})
    private Long userId;

    private Timestamp datePosted;
    private Timestamp dateLastActive;
    private int views;
    private int likes;
    private int replies;

    private List<DiscussionReplyTo> discussionReplyToList;

    public DiscussionTopicTo(Long id, String topic, Long userId) {
        this.id = id;
        this.topic = topic;
        this.userId = userId;
    }

    public List<DiscussionReplyTo> getDiscussionReplyToList() {
        return discussionReplyToList;
    }

    public void setDiscussionReplyToList(List<DiscussionReplyTo> discussionReplyToList) {
        this.discussionReplyToList = discussionReplyToList;
    }

    public DiscussionTopicTo() {
    }

    public Long getId() {
        return id;
    }

    public String getTopic() {
        return topic;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Timestamp getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(Timestamp datePosted) {
        this.datePosted = datePosted;
    }

    public Timestamp getDateLastActive() {
        return dateLastActive;
    }

    public void setDateLastActive(Timestamp dateLastActive) {
        this.dateLastActive = dateLastActive;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getReplies() {
        return replies;
    }

    public void setReplies(int replies) {
        this.replies = replies;
    }
}
