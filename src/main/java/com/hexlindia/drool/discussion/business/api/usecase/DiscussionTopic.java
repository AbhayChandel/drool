package com.hexlindia.drool.discussion.business.api.usecase;

import com.hexlindia.drool.discussion.dto.DiscussionTopicDto;

public interface DiscussionTopic {

    DiscussionTopicDto post(DiscussionTopicDto discussionTopicDto);

    DiscussionTopicDto findById(String id);

    boolean updateTopicTitle(DiscussionTopicDto discussionTopicDto);

    String incrementViews(String id);

    String incrementLikes(DiscussionTopicDto discussionTopicDto);

    String decrementLikes(DiscussionTopicDto discussionTopicDto);

    DiscussionTopicDto changeOwnership(DiscussionTopicDto discussionTopicDto);
}
