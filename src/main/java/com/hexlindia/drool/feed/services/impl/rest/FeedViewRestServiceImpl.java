package com.hexlindia.drool.feed.services.impl.rest;

import com.hexlindia.drool.feed.business.api.FeedView;
import com.hexlindia.drool.feed.services.api.rest.FeedViewRestService;
import com.hexlindia.drool.feed.view.FeedItemPreview;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FeedViewRestServiceImpl implements FeedViewRestService {

    private final FeedView feedView;

    @Override
    public ResponseEntity<List<FeedItemPreview>> findById(int pageNumber, int pageSize) {
        return ResponseEntity.ok(feedView.getFeedPage(pageNumber, pageSize));
    }
}
