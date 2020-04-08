package com.hexlindia.drool.activity.services.api.rest;

import com.hexlindia.drool.activity.dto.FeedDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/${rest.uri.version}/view/activity")
public interface ActivityFeedRestService {

    @GetMapping("/feed/{page}")
    ResponseEntity<List<FeedDto>> getFeed(@PathVariable("page") int page);
}
