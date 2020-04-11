package com.hexlindia.drool.discussion.services.iml.rest;

import com.hexlindia.drool.discussion.business.api.usecase.DiscussionTopic;
import com.hexlindia.drool.discussion.dto.DiscussionTopicDto;
import com.hexlindia.drool.discussion.services.api.rest.DiscussionViewRestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DiscussionViewRestServiceImpl implements DiscussionViewRestService {

    private final DiscussionTopic discussionTopic;

    @Override
    public ResponseEntity<DiscussionTopicDto> findById(String id) {
        return ResponseEntity.ok(this.discussionTopic.findById(id));
    }
}
