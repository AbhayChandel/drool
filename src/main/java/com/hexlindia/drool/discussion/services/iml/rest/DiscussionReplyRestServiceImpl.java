package com.hexlindia.drool.discussion.services.iml.rest;

import com.hexlindia.drool.discussion.business.api.usecase.DiscussionReply;
import com.hexlindia.drool.discussion.dto.DiscussionReplyDto;
import com.hexlindia.drool.discussion.services.api.rest.DiscussionReplyRestService;
import com.hexlindia.drool.discussion.to.validation.DiscussionReplyPostValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class DiscussionReplyRestServiceImpl implements DiscussionReplyRestService {

    private final DiscussionReply discussionReply;

    @Override
    public ResponseEntity<DiscussionReplyDto> saveReply(@Validated(DiscussionReplyPostValidation.class) DiscussionReplyDto discussionReplyDto) {
        return ResponseEntity.ok(this.discussionReply.saveReply(discussionReplyDto));
    }

    @Override
    public ResponseEntity<Boolean> updateReply(Map<String, String> parameters) {
        return ResponseEntity.ok(this.discussionReply.updateReply(parameters.get("reply"), parameters.get("replyId"), parameters.get("discussionId")));
    }

    @Override
    public ResponseEntity<String> incrementLikes(Map<String, String> parameters) {
        return ResponseEntity.ok(discussionReply.incrementLikes(parameters.get("replyId"), parameters.get("discussionId"), parameters.get("userId")));
    }

    @Override
    public ResponseEntity<String> decrementLikes(Map<String, String> parameters) {
        return ResponseEntity.ok(discussionReply.decrementLikes(parameters.get("replyId"), parameters.get("discussionId"), parameters.get("userId")));
    }

    @Override
    public ResponseEntity<Boolean> setStatus(Map<String, String> parameters) {
        return ResponseEntity.ok(this.discussionReply.setStatus(Boolean.valueOf(parameters.get("status")), parameters.get("replyId"), parameters.get("discussionId")));
    }
}
