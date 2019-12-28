package com.hexlindia.drool.discussion.business.api.usecase;

import com.hexlindia.drool.discussion.data.entity.DiscussionReplyUserLikeId;

public interface DiscussionReplyUserLike {

    void save(DiscussionReplyUserLikeId id);

    void remove(DiscussionReplyUserLikeId id);
}
