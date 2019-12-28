package com.hexlindia.drool.discussion.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "discussion_reply_activity")
public class DiscussionReplyActivityEntity {

    @Id
    @Column(name = "discussion_reply_id")
    private Long discussionTopicId;

    private Timestamp datePosted;

    private int likes;

    public DiscussionReplyActivityEntity() {
    }

    public DiscussionReplyActivityEntity(Long discussionTopicId) {
        this.discussionTopicId = discussionTopicId;
    }

    public Long getDiscussionTopicId() {
        return discussionTopicId;
    }

    public void setDiscussionTopicId(Long discussionTopicId) {
        this.discussionTopicId = discussionTopicId;
    }

    public Timestamp getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(Timestamp datePosted) {
        this.datePosted = datePosted;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
