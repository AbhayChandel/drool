package com.hexlindia.drool.discussion.services.api.rest;

import com.hexlindia.drool.discussion.dto.DiscussionReplyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping("/${rest.uri.version}/discussion/reply")
public interface DiscussionReplyRestService {

    @PostMapping(value = "/post")
    ResponseEntity<DiscussionReplyDto> saveReply(@RequestBody DiscussionReplyDto discussionReplyDto);

    @PutMapping(value = "/update")
    ResponseEntity<Boolean> updateReply(@RequestBody Map<String, String> parameters);

    @PutMapping(value = "/likes/increment")
    ResponseEntity<String> incrementLikes(@RequestBody Map<String, String> parameters);

    @PutMapping(value = "/likes/decrement")
    ResponseEntity<String> decrementLikes(@RequestBody Map<String, String> parameters);

    @PutMapping(value = "/set")
    ResponseEntity<Boolean> setStatus(@RequestBody Map<String, String> parameters);
}
