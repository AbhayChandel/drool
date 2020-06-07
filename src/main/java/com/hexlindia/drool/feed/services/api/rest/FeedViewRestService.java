package com.hexlindia.drool.feed.services.api.rest;

import com.hexlindia.drool.feed.view.FeedItemPreview;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/${rest.uri.version}/view/feed")
public interface FeedViewRestService {

    @GetMapping
    ResponseEntity<List<FeedItemPreview>> findById(@RequestParam(name = "pno", defaultValue = "0") int pageNumber, @RequestParam(name = "psize", defaultValue = "7") int pageSize);
}
