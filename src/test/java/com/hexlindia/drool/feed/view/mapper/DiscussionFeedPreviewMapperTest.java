package com.hexlindia.drool.feed.view.mapper;

import com.hexlindia.drool.discussion2.view.DiscussionPreview;
import com.hexlindia.drool.feed.view.FeedItemPreview;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DiscussionFeedPreviewMapperTest {

    @Autowired
    DiscussionFeedPreviewMapper discussionFeedPreviewMapper;

    @Test
    void toFeedPreview() {
        DiscussionPreview discussionPreview = new DiscussionPreview(400076, "This is a discussion to test mapper", 232454L,
                14565L, LocalDateTime.of(2020, 01, 11, 23, 1), 100987, "cuckoo");
        FeedItemPreview feedItemPreview = discussionFeedPreviewMapper.toFeedPreview(discussionPreview);
        assertEquals("400076", feedItemPreview.getId());
        assertEquals("discussion", feedItemPreview.getItemType());
        assertEquals("This is a discussion to test mapper", feedItemPreview.getTitle());
        assertEquals("232.4k", feedItemPreview.getLikes());
        assertEquals("14.5k", feedItemPreview.getComments());
        assertEquals(LocalDateTime.of(2020, 01, 11, 23, 1), feedItemPreview.getDatePosted());
        assertEquals("100987", feedItemPreview.getUserProfilePreview().getId());
        assertEquals("cuckoo", feedItemPreview.getUserProfilePreview().getUsername());
    }

    @Test
    void toFeedPreviewList() {
        List<DiscussionPreview> discussionPreviewList = Arrays.asList(new DiscussionPreview(1, "", 1L, 1L, null, 1L, ""),
                new DiscussionPreview(1, "", 1L, 1L, null, 1L, ""),
                new DiscussionPreview(1, "", 1L, 1L, null, 1L, ""));
        assertEquals(3, discussionFeedPreviewMapper.toFeedPreviewList(discussionPreviewList).size());
    }
}