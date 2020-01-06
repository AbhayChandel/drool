package com.hexlindia.drool.discussion.services.api.rest;

import com.hexlindia.drool.discussion.view.DiscussionPageView;
import com.hexlindia.drool.discussion.view.DiscussionTopicCardView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/${rest.uri.version}/discussion/view")
public interface DiscussionViewRestService {

    @GetMapping(value = "/topic/id/{id}")
    ResponseEntity<DiscussionTopicCardView> findDiscussionTopicCardViewById(@PathVariable("id") Long id);

    @GetMapping(value = "/page/id/{id}")
    ResponseEntity<DiscussionPageView> findDiscussionPageViewById(@PathVariable("id") Long id);

}
