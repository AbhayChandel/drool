package com.hexlindia.drool.discussion.data.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class DiscussionTopicUserLikeId implements Serializable {

    private Long userId;
    private Long topicId;

    public DiscussionTopicUserLikeId() {
    }

    public DiscussionTopicUserLikeId(Long userId, Long topicId) {
        this.userId = userId;
        this.topicId = topicId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getTopicId() {
        return topicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscussionTopicUserLikeId that = (DiscussionTopicUserLikeId) o;
        return userId.equals(that.userId) &&
                topicId.equals(that.topicId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, topicId);
    }
}
