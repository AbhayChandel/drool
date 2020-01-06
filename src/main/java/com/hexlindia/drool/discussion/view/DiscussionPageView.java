package com.hexlindia.drool.discussion.view;

import java.util.List;

public class DiscussionPageView {

    private final DiscussionTopicCardView discussionTopicCardView;
    private final List<DiscussionReplyCardView> discussionReplyCardViewList;

    public DiscussionPageView(DiscussionTopicCardView discussionTopicCardView, List<DiscussionReplyCardView> discussionReplyCardViewList) {
        this.discussionTopicCardView = discussionTopicCardView;
        this.discussionReplyCardViewList = discussionReplyCardViewList;
    }

    public DiscussionTopicCardView getDiscussionTopicCardView() {
        return discussionTopicCardView;
    }

    public List<DiscussionReplyCardView> getDiscussionReplyCardViewList() {
        return discussionReplyCardViewList;
    }
}
