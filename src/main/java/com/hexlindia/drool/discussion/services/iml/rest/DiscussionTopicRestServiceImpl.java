package com.hexlindia.drool.discussion.services.iml.rest;

import com.hexlindia.drool.discussion.business.api.usecase.DiscussionTopic;
import com.hexlindia.drool.discussion.dto.DiscussionTopicDto;
import com.hexlindia.drool.discussion.services.api.rest.DiscussionTopicRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
    public ResponseEntity<Boolean> updateTitle(Map<String, String> parameters) {
        return ResponseEntity.ok(this.discussionTopic.updateTopicTitle(parameters.get("title"), parameters.get("id")));
    }

    @Override
    public ResponseEntity<String> incrementViews(String id) {
        return ResponseEntity.ok(discussionTopic.incrementViews(id));
    }

    @Override
    public ResponseEntity<String> incrementLikes(Map<String, String> parameters) {
        return ResponseEntity.ok(discussionTopic.incrementLikes(parameters.get("id"), parameters.get("userId")));
    }

    @Override
    public ResponseEntity<String> decrementLikes(Map<String, String> parameters) {
        return ResponseEntity.ok(discussionTopic.decrementLikes(parameters.get("id"), parameters.get("userId")));
    }
}
