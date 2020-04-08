package com.hexlindia.drool.activity.services.impl.rest;

import com.hexlindia.drool.activity.business.api.usecase.ActivityFeed;
import com.hexlindia.drool.activity.dto.FeedDto;
import com.hexlindia.drool.activity.services.api.rest.ActivityFeedRestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ActivityFeedRestServiceImpl implements ActivityFeedRestService {

    private final ActivityFeed activityFeed;

    @Override
    public ResponseEntity<List<FeedDto>> getFeed(int page) {
        return ResponseEntity.ok(activityFeed.getFeed(page));
    }
}
