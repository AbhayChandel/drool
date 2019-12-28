package com.hexlindia.drool.discussion.business.api.usecase;

import com.hexlindia.drool.common.to.ActivityTo;
import com.hexlindia.drool.discussion.data.entity.DiscussionReplyEntity;
import com.hexlindia.drool.discussion.to.DiscussionTopicTo;

public interface DiscussionTopic {

    DiscussionTopicTo post(DiscussionTopicTo discussionTopicTo);

    DiscussionTopicTo findById(Long id);

    DiscussionTopicTo updateTopicTitle(DiscussionTopicTo discussionTopicTo);

    void incrementViewsByOne(Long id);

    void incrementLikesByOne(ActivityTo activityTo);

    void decrementLikesByOne(ActivityTo activityTo);

    void incrementRepliesByOne(Long id);

    void decrementRepliesByOne(Long id);

    void setLastDateActiveToNow(Long id);

    void saveReply(DiscussionReplyEntity discussionReplyEntity, Long discussionTopicId);
}
