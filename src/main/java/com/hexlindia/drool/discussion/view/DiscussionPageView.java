package com.hexlindia.drool.discussion.view;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DiscussionPageView {

    @JsonProperty("topicCard")
    private final DiscussionTopicCardView discussionTopicCardView;

    @JsonProperty("replyCardList")
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
