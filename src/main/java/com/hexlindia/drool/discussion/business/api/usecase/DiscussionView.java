package com.hexlindia.drool.discussion.business.api.usecase;

import com.hexlindia.drool.discussion.view.DiscussionPageView;
import com.hexlindia.drool.discussion.view.DiscussionTopicCardView;

public interface DiscussionView {

    DiscussionTopicCardView getDicussionTopicCardView(Long discussionTopicId);

    DiscussionPageView getDiscussionPageView(Long discussionTopicId);
}
