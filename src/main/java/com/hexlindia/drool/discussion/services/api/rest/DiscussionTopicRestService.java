package com.hexlindia.drool.discussion.services.api.rest;

import com.hexlindia.drool.common.to.ActivityTo;
import com.hexlindia.drool.discussion.to.DiscussionTopicTo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/${rest.uri.version}/discussion")
public interface DiscussionTopicRestService {
    @PostMapping(value = "/post")
    ResponseEntity<DiscussionTopicTo> post(@RequestBody DiscussionTopicTo discussionTopicTo);

    @GetMapping(value = "/find/id/{id}")
    ResponseEntity<DiscussionTopicTo> findById(@PathVariable("id") Long id);

    @PutMapping(value = "/update")
    ResponseEntity<DiscussionTopicTo> updateTitle(@RequestBody DiscussionTopicTo discussionTopicTo);

    @PutMapping(value = "/views/increment")
    ResponseEntity<String> incrementViewsCount(@RequestBody Long id);

    @PutMapping(value = "/likes/increment")
    ResponseEntity<String> incrementLikesCount(@RequestBody ActivityTo activityTo);

    @PutMapping(value = "/likes/decrement")
    ResponseEntity<String> decrementLikesCount(@RequestBody ActivityTo activityTo);

}
