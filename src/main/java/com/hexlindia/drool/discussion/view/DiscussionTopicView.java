package com.hexlindia.drool.discussion.view;

public class DiscussionTopicView {

    private Long topicId;
    private String topic;
    private Long userId;
    private String datePosted;
    private String dateLastActive;
    private int views;
    private int likes;
    private int replies;

    public DiscussionTopicView(Long topicId, String topic, Long userId, String datePosted, String dateLastActive, int views, int likes, int replies) {
        this.topicId = topicId;
        this.topic = topic;
        this.userId = userId;
        this.datePosted = datePosted;
        this.dateLastActive = dateLastActive;
        this.views = views;
        this.likes = likes;
        this.replies = replies;
    }

    public Long getTopicId() {
        return topicId;
    }

    public String getTopic() {
        return topic;
    }

    public Long getUserId() {
        return userId;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public String getDateLastActive() {
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
}
