package com.hexlindia.drool.discussion.view;

public class DiscussionTopicView {

    private String topicId;
    private String topic;
    private String userId;
    private String datePosted;
    private String dateLastActive;
    private String views;
    private String likes;
    private String replies;

    public DiscussionTopicView(String topicId, String topic, String userId, String datePosted, String dateLastActive, String views, String likes, String replies) {
        this.topicId = topicId;
        this.topic = topic;
        this.userId = userId;
        this.datePosted = datePosted;
        this.dateLastActive = dateLastActive;
        this.views = views;
        this.likes = likes;
        this.replies = replies;
    }

    public String getTopicId() {
        return topicId;
    }

    public String getTopic() {
        return topic;
    }

    public String getUserId() {
        return userId;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public String getDateLastActive() {
        return dateLastActive;
    }

    public String getViews() {
        return views;
    }

    public String getLikes() {
        return likes;
    }

    public String getReplies() {
        return replies;
    }
}
