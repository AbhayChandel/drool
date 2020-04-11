package com.hexlindia.drool.discussion.services.api.rest;

import com.hexlindia.drool.discussion.dto.DiscussionTopicDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping("/${rest.uri.version}/discussion")
public interface DiscussionTopicRestService {

    @PostMapping(value = "/post")
    ResponseEntity<DiscussionTopicDto> post(@RequestBody DiscussionTopicDto discussionTopicDto);

    @PutMapping(value = "/update")
    ResponseEntity<Boolean> updateTitle(@RequestBody Map<String, String> parameters);

    @PutMapping(value = "/views/increment")
    ResponseEntity<String> incrementViews(@RequestBody String id);

    @PutMapping(value = "/likes/increment")
    ResponseEntity<String> incrementLikes(@RequestBody Map<String, String> parameters);

    @PutMapping(value = "/likes/decrement")
    ResponseEntity<String> decrementLikes(@RequestBody Map<String, String> parameters);

}
