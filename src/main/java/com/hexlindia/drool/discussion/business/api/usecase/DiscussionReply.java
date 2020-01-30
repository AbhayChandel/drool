package com.hexlindia.drool.discussion.business.api.usecase;

import com.hexlindia.drool.common.to.ActivityTo;
import com.hexlindia.drool.discussion.to.DiscussionReplyTo;

public interface DiscussionReply {

    DiscussionReplyTo post(DiscussionReplyTo discussionTopicTo);

    DiscussionReplyTo findById(Long id);

    DiscussionReplyTo updateReply(DiscussionReplyTo discussionReplyTo);

    String incrementLikesByOne(ActivityTo activityTo);

    String decrementLikesByOne(ActivityTo activityTo);

    DiscussionReplyTo deactivateReply(Long id);
}
