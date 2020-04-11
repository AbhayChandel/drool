package com.hexlindia.drool.discussion.services.api.rest;

import com.hexlindia.drool.discussion.dto.DiscussionTopicDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/${rest.uri.version}/view/discussion")
public interface DiscussionViewRestService {

    @GetMapping(value = "/find/id/{id}")
    ResponseEntity<DiscussionTopicDto> findById(@PathVariable("id") String id);

}
