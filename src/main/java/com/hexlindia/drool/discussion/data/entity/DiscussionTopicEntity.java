package com.hexlindia.drool.discussion.data.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "discussion_topic")
public class DiscussionTopicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "topic_id_generator")
    @SequenceGenerator(name = "topic_id_generator", sequenceName = "discussion_topic_seq", allocationSize = 1)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    private DiscussionTopicActivityEntity discussionTopicActivityEntity;

    @OneToMany(
            mappedBy = "discussionTopicEntity",
            orphanRemoval = true
    )
    private List<DiscussionReplyEntity> discussionReplyEntityList = new ArrayList<>();

    private String topic;
    private Long userId;
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

    public DiscussionTopicActivityEntity getDiscussionTopicActivityEntity() {
        return discussionTopicActivityEntity;
    }

    public void setDiscussionTopicActivityEntity(DiscussionTopicActivityEntity discussionTopicActivityEntity) {
        this.discussionTopicActivityEntity = discussionTopicActivityEntity;
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
}
