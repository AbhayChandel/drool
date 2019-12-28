package com.hexlindia.drool.discussion.data.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "discussion_topic_user_like")
public class DiscussionTopicUserLikeEntity {

    @EmbeddedId
    private DiscussionTopicUserLikeId id;

    public DiscussionTopicUserLikeEntity(DiscussionTopicUserLikeId id) {
        this.id = id;
    }

    public DiscussionTopicUserLikeEntity() {
    }

    public DiscussionTopicUserLikeId getId() {
        return id;
    }

    public void setId(DiscussionTopicUserLikeId id) {
        this.id = id;
    }
}
