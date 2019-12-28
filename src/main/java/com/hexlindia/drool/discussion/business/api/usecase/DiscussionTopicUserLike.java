package com.hexlindia.drool.discussion.business.api.usecase;

import com.hexlindia.drool.discussion.data.entity.DiscussionTopicUserLikeId;

public interface DiscussionTopicUserLike {

    void save(DiscussionTopicUserLikeId discussionTopicUserLikeId);

    void remove(DiscussionTopicUserLikeId discussionTopicUserLikeId);
}
