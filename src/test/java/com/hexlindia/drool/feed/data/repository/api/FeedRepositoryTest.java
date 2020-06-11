package com.hexlindia.drool.feed.data.repository.api;

import com.hexlindia.drool.feed.data.entity.FeedEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class FeedRepositoryTest {

    @Autowired
    private FeedRepository feedRepository;

    @Test
    void getFeedEntities() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("datePosted").descending());
        Page<FeedEntity> feedEntityList = feedRepository.findAll(pageable);
        assertEquals(6, feedEntityList.getTotalElements());
    }

}