package com.hexlindia.drool.discussion.services.iml.rest;

import com.hexlindia.drool.discussion.business.api.usecase.DiscussionView;
import com.hexlindia.drool.discussion.services.api.rest.DiscussionViewRestService;
import com.hexlindia.drool.discussion.view.DiscussionPageView;
import com.hexlindia.drool.discussion.view.DiscussionTopicCardView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiscussionViewRestServiceImpl implements DiscussionViewRestService {

    private final DiscussionView discussionView;

    public DiscussionViewRestServiceImpl(DiscussionView discussionView) {
        this.discussionView = discussionView;
    }

    @Override
    public ResponseEntity<DiscussionTopicCardView> findDiscussionTopicCardViewById(Long id) {
        return ResponseEntity.ok(this.discussionView.getDicussionTopicCardView(id));
    }

    @Override
    public ResponseEntity<DiscussionPageView> findDiscussionPageViewById(Long id) {
        return ResponseEntity.ok(this.discussionView.getDiscussionPageView(id));
    }
}
