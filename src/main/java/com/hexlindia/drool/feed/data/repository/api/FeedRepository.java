package com.hexlindia.drool.feed.data.repository.api;

import com.hexlindia.drool.feed.data.entity.FeedEntity;
import com.hexlindia.drool.feed.data.entity.FeedEntityId;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FeedRepository extends PagingAndSortingRepository<FeedEntity, FeedEntityId> {
}
