package com.hexlindia.drool.discussion.data.entity;

import javax.persistence.*;

@Entity
@Table(name = "discussion_reply")
public class DiscussionReplyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reply_id_generator")
    @SequenceGenerator(name = "reply_id_generator", sequenceName = "discussion_reply_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discussion_topic_id")
    private DiscussionTopicEntity discussionTopicEntity;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    private DiscussionReplyActivityEntity discussionReplyActivityEntity;

    private String reply;
    private Long userId;
    private boolean active;

    public DiscussionReplyEntity(String reply, Long userId) {
        this.reply = reply;
        this.userId = userId;
    }

    public DiscussionReplyEntity() {
    }

    public DiscussionTopicEntity getDiscussionTopicEntity() {
        return discussionTopicEntity;
    }

    public void setDiscussionTopicEntity(DiscussionTopicEntity discussionTopicEntity) {
        this.discussionTopicEntity = discussionTopicEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DiscussionReplyActivityEntity getDiscussionReplyActivityEntity() {
        return discussionReplyActivityEntity;
    }

    public void setDiscussionReplyActivityEntity(DiscussionReplyActivityEntity discussionReplyActivityEntity) {
        this.discussionReplyActivityEntity = discussionReplyActivityEntity;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
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
