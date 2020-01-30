package com.hexlindia.drool.discussion.services.iml.rest;

import com.hexlindia.drool.common.to.ActivityTo;
import com.hexlindia.drool.discussion.business.api.usecase.DiscussionReply;
import com.hexlindia.drool.discussion.services.api.rest.DiscussionReplyRestService;
import com.hexlindia.drool.discussion.to.DiscussionReplyTo;
import com.hexlindia.drool.discussion.to.validation.DiscussionReplyPostValidation;
import com.hexlindia.drool.discussion.to.validation.DiscussionReplyUpdateValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiscussionReplyRestServiceImpl implements DiscussionReplyRestService {

    @Autowired
    DiscussionReply discussionReply;


    @Override
    public ResponseEntity<DiscussionReplyTo> post(@Validated(DiscussionReplyPostValidation.class) DiscussionReplyTo discussionReplyTo) {
        return ResponseEntity.ok(this.discussionReply.post(discussionReplyTo));
    }

    @Override
    public ResponseEntity<DiscussionReplyTo> updateReply(@Validated(DiscussionReplyUpdateValidation.class) DiscussionReplyTo discussionReplyTo) {
        return ResponseEntity.ok(this.discussionReply.updateReply(discussionReplyTo));
    }

    @Override
    public ResponseEntity<DiscussionReplyTo> deleteReplyById(Long id) {
        return ResponseEntity.ok(this.discussionReply.deactivateReply(id));
    }

    @Override
    public ResponseEntity<DiscussionReplyTo> findById(Long id) {
        return ResponseEntity.ok(this.discussionReply.findById(id));
    }

    @Override
    public ResponseEntity<String> incrementLikesCount(ActivityTo activityTo) {
        return ResponseEntity.ok(discussionReply.incrementLikesByOne(activityTo));
    }

    @Override
    public ResponseEntity<String> decrementLikesCount(ActivityTo activityTo) {
        return ResponseEntity.ok(discussionReply.decrementLikesByOne(activityTo));
    }
}
