package com.hexlindia.drool.discussion.business.impl.usecase;

import com.hexlindia.drool.discussion.business.api.usecase.DiscussionView;
import com.hexlindia.drool.discussion.data.repository.api.DiscussionViewRepository;
import com.hexlindia.drool.discussion.view.DiscussionPageView;
import com.hexlindia.drool.discussion.view.DiscussionTopicCardView;
import org.springframework.stereotype.Component;

@Component
public class DiscussionViewImpl implements DiscussionView {

    private final DiscussionViewRepository discussionViewRepository;

    public DiscussionViewImpl(DiscussionViewRepository discussionViewRepository) {
        this.discussionViewRepository = discussionViewRepository;
    }

    @Override
    public DiscussionTopicCardView getDicussionTopicCardView(Long discussionTopicId) {
        return discussionViewRepository.getDiscussionTopicCardView(discussionTopicId);
    }

    @Override
    public DiscussionPageView getDiscussionPageView(Long discussionTopicId) {
        return new DiscussionPageView(discussionViewRepository.getDiscussionTopicCardView(discussionTopicId), discussionViewRepository.getDicussionReplyCardViews(discussionTopicId));
    }
}
