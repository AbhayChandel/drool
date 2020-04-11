package com.hexlindia.drool.discussion.business.api.usecase;

import com.hexlindia.drool.discussion.dto.DiscussionTopicDto;

public interface DiscussionTopic {

    DiscussionTopicDto post(DiscussionTopicDto discussionTopicDto);

    DiscussionTopicDto findById(String id);

    boolean updateTopicTitle(String title, String id);

    String incrementViews(String id);

    String incrementLikes(String id, String userId);

    String decrementLikes(String id, String userId);
}
