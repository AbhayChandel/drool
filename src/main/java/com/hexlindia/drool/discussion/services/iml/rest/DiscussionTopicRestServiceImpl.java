package com.hexlindia.drool.discussion.services.iml.rest;

import com.hexlindia.drool.discussion.business.api.usecase.DiscussionTopic;
import com.hexlindia.drool.discussion.dto.DiscussionTopicDto;
import com.hexlindia.drool.discussion.services.api.rest.DiscussionTopicRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiscussionTopicRestServiceImpl implements DiscussionTopicRestService {

    private final DiscussionTopic discussionTopic;

    @Autowired
    public DiscussionTopicRestServiceImpl(DiscussionTopic discussionTopic) {
        this.discussionTopic = discussionTopic;
    }

    @Override
    public ResponseEntity<DiscussionTopicDto> post(DiscussionTopicDto discussionTopicDto) {
        return ResponseEntity.ok(this.discussionTopic.post(discussionTopicDto));
    }

    @Override
    public ResponseEntity<Boolean> updateTitle(DiscussionTopicDto discussionTopicDto) {
        return ResponseEntity.ok(this.discussionTopic.updateTopicTitle(discussionTopicDto));
    }

    @Override
    public ResponseEntity<String> incrementViews(String id) {
        return ResponseEntity.ok(discussionTopic.incrementViews(id));
    }

    @Override
    public ResponseEntity<String> incrementLikes(DiscussionTopicDto discussionTopicDto) {
        return ResponseEntity.ok(discussionTopic.incrementLikes(discussionTopicDto));
    }

    @Override
    public ResponseEntity<String> decrementLikes(DiscussionTopicDto discussionTopicDto) {
        return ResponseEntity.ok(discussionTopic.decrementLikes(discussionTopicDto));
    }

    @Override
    public ResponseEntity<DiscussionTopicDto> changeOwnership(DiscussionTopicDto discussionTopicDto) {
        return ResponseEntity.ok(this.discussionTopic.changeOwnership(discussionTopicDto));
    }
}
