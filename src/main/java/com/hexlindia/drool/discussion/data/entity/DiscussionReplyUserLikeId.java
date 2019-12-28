package com.hexlindia.drool.discussion.data.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class DiscussionReplyUserLikeId implements Serializable {
    private Long userId;
    private Long replyId;

    public DiscussionReplyUserLikeId(Long userId, Long replyId) {
        this.userId = userId;
        this.replyId = replyId;
    }

    public DiscussionReplyUserLikeId() {
    }

    public Long getUserId() {
        return userId;
    }

    public Long getReplyId() {
        return replyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscussionReplyUserLikeId that = (DiscussionReplyUserLikeId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(replyId, that.replyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, replyId);
    }
}
