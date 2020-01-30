package com.hexlindia.drool.discussion.services.iml.rest;

import com.hexlindia.drool.common.to.ActivityTo;
import com.hexlindia.drool.discussion.business.api.usecase.DiscussionTopic;
import com.hexlindia.drool.discussion.services.api.rest.DiscussionTopicRestService;
import com.hexlindia.drool.discussion.to.DiscussionTopicTo;
import com.hexlindia.drool.discussion.to.validation.DiscussionTopicCreateValidation;
import com.hexlindia.drool.discussion.to.validation.DiscussionTopicUpdateValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiscussionTopicRestServiceImpl implements DiscussionTopicRestService {

    private final DiscussionTopic discussionTopic;

    @Autowired
    public DiscussionTopicRestServiceImpl(DiscussionTopic discussionTopic) {
        this.discussionTopic = discussionTopic;
    }

    @Override
    public ResponseEntity<DiscussionTopicTo> post(@Validated(DiscussionTopicCreateValidation.class) DiscussionTopicTo discussionTopicTo) {
        return ResponseEntity.ok(this.discussionTopic.post(discussionTopicTo));
    }

    @Override
    public ResponseEntity<DiscussionTopicTo> updateTitle(@Validated(DiscussionTopicUpdateValidation.class) DiscussionTopicTo discussionTopicTo) {
        return ResponseEntity.ok(this.discussionTopic.updateTopicTitle(discussionTopicTo));
    }

    @Override
    public ResponseEntity incrementViewsCount(Long id) {
        discussionTopic.incrementViewsByOne(id);
        return ResponseEntity.ok("");
    }

    @Override
    public ResponseEntity<String> incrementLikesCount(ActivityTo activityTo) {
        return ResponseEntity.ok(discussionTopic.incrementLikesByOne(activityTo));
    }

    @Override
    public ResponseEntity<String> decrementLikesCount(ActivityTo activityTo) {
        return ResponseEntity.ok(discussionTopic.decrementLikesByOne(activityTo));
    }

    @Override
    public ResponseEntity<DiscussionTopicTo> findById(Long id) {
        return ResponseEntity.ok(this.discussionTopic.findById(id));
    }
}
