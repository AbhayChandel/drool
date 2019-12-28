package com.hexlindia.drool.discussion.services.api.rest;

import com.hexlindia.drool.common.to.ActivityTo;
import com.hexlindia.drool.discussion.to.DiscussionReplyTo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/${rest.uri.version}/discussion/reply")
public interface DiscussionReplyRestService {

    @PostMapping(value = "/post")
    ResponseEntity<DiscussionReplyTo> post(@RequestBody DiscussionReplyTo discussionReplyTo);

    @PutMapping(value = "/update")
    ResponseEntity<DiscussionReplyTo> updateReply(@RequestBody DiscussionReplyTo discussionReplyTo);

    @DeleteMapping(value = "/delete/id/{id}")
    ResponseEntity<DiscussionReplyTo> deleteReplyById(@PathVariable("id") Long id);

    @GetMapping(value = "/find/id/{id}")
    ResponseEntity<DiscussionReplyTo> findById(@PathVariable("id") Long id);

    @PutMapping(value = "/likes/increment")
    ResponseEntity<String> incrementLikesCount(@RequestBody ActivityTo activityTo);

    @PutMapping(value = "/likes/decrement")
    ResponseEntity<String> decrementLikesCount(@RequestBody ActivityTo activityTo);
}
