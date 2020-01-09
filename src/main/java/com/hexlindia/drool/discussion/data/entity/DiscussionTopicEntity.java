package com.hexlindia.drool.discussion.data.entity;

import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "discussion_topic")
public class DiscussionTopicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "topic_id_generator")
    @SequenceGenerator(name = "topic_id_generator", sequenceName = "discussion_topic_id_seq", allocationSize = 1)
    private Long id;

    @OneToMany(
            mappedBy = "discussionTopicEntity",
            orphanRemoval = true
    )
    @Where(clause = "active = 'true'")
    private List<DiscussionReplyEntity> discussionReplyEntityList = new ArrayList<>();

    private String topic;
    private Long userId;
    private LocalDateTime datePosted;
    private LocalDateTime dateLastActive;
    private int views;
    private int likes;
    private int replies;
    private boolean active;

    public DiscussionTopicEntity() {
    }

    public DiscussionTopicEntity(String topic, Long userId, boolean active) {
        this.topic = topic;
        this.userId = userId;
        this.active = active;
    }

    public List<DiscussionReplyEntity> getDiscussionReplyEntityList() {
        return discussionReplyEntityList;
    }

    public void setDiscussionReplyEntityList(List<DiscussionReplyEntity> discussionReplyEntityList) {
        this.discussionReplyEntityList = discussionReplyEntityList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(LocalDateTime datePosted) {
        this.datePosted = datePosted;
    }

    public LocalDateTime getDateLastActive() {
        return dateLastActive;
    }

    public void setDateLastActive(LocalDateTime dateLastActive) {
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
