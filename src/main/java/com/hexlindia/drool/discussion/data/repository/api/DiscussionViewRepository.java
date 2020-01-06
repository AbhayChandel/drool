package com.hexlindia.drool.discussion.data.repository.api;

import com.hexlindia.drool.discussion.view.DiscussionReplyCardView;
import com.hexlindia.drool.discussion.view.DiscussionTopicCardView;

import java.util.List;

public interface DiscussionViewRepository {

    DiscussionTopicCardView getDiscussionTopicCardView(Long discussionTopicId);

    List<DiscussionReplyCardView> getDicussionReplyCardViews(Long discussionTopicId);
}
