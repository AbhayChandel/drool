package com.hexlindia.drool.discussion.data.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "discussion_reply_user_like")
public class DiscussionReplyUserLikeEntity {

    @EmbeddedId
    private DiscussionReplyUserLikeId id;

    public DiscussionReplyUserLikeEntity(DiscussionReplyUserLikeId id) {
        this.id = id;
    }

    public DiscussionReplyUserLikeEntity() {
    }

    public DiscussionReplyUserLikeId getId() {
        return id;
    }

    public void setId(DiscussionReplyUserLikeId id) {
        this.id = id;
    }
}
