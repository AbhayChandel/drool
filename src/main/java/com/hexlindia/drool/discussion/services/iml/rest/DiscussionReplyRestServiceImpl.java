package com.hexlindia.drool.discussion.services.iml.rest;

import com.hexlindia.drool.discussion.business.api.usecase.DiscussionReply;
import com.hexlindia.drool.discussion.dto.DiscussionReplyDto;
import com.hexlindia.drool.discussion.services.api.rest.DiscussionReplyRestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class DiscussionReplyRestServiceImpl implements DiscussionReplyRestService {

    private final DiscussionReply discussionReply;

    private static final String PARAM_REPLY_ID = "replyId";
    private static final String PARAM_USER_ID = "userId";
    private static final String PARAM_DISCUSSION_ID = "discussionId";

    @Override
    public ResponseEntity<DiscussionReplyDto> saveReply(DiscussionReplyDto discussionReplyDto) {
        return ResponseEntity.ok(this.discussionReply.saveOrUpdate(discussionReplyDto));
    }

    @Override
    public ResponseEntity<Boolean> updateReply(Map<String, String> parameters) {
        //return ResponseEntity.ok(this.discussionReply.updateReply(parameters.get("reply"), parameters.get(PARAM_REPLY_ID), parameters.get(PARAM_DISCUSSION_ID)));
        return null;
    }

    @Override
    public ResponseEntity<String> incrementLikes(Map<String, String> parameters) {
        return ResponseEntity.ok(discussionReply.incrementLikes(parameters.get(PARAM_REPLY_ID), parameters.get(PARAM_DISCUSSION_ID), parameters.get(PARAM_USER_ID)));
    }

    @Override
    public ResponseEntity<String> decrementLikes(Map<String, String> parameters) {
        return ResponseEntity.ok(discussionReply.decrementLikes(parameters.get(PARAM_REPLY_ID), parameters.get(PARAM_DISCUSSION_ID), parameters.get(PARAM_USER_ID)));
    }

    @Override
    public ResponseEntity<Boolean> setStatus(Map<String, String> parameters) {
        return ResponseEntity.ok(this.discussionReply.setStatus(Boolean.valueOf(parameters.get("status")), parameters.get(PARAM_REPLY_ID), parameters.get(PARAM_DISCUSSION_ID)));
    }
}
