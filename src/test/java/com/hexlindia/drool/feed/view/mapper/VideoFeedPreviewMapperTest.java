package com.hexlindia.drool.feed.view.mapper;

import com.hexlindia.drool.feed.view.FeedItemPreview;
import com.hexlindia.drool.video2.view.VideoPreview;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class VideoFeedPreviewMapperTest {

    @Autowired
    VideoFeedPreviewMapper videoFeedPreviewMapper;

    @Test
    void toFeedPreview() {
        VideoPreview videoPreview = new VideoPreview(103456001, "This is a video to test mapper", "se56z45", 23546L,
                1234, LocalDateTime.of(2019, 11, 15, 8, 55), 300098, "buzzinga77");
        FeedItemPreview feedItemPreview = videoFeedPreviewMapper.toFeedPreview(videoPreview);
        assertEquals("103456001", feedItemPreview.getId());
        assertEquals("video", feedItemPreview.getItemType());
        assertEquals("This is a video to test mapper", feedItemPreview.getTitle());
        assertEquals("23.5k", feedItemPreview.getLikes());
        assertEquals("1.2k", feedItemPreview.getComments());
        assertEquals(LocalDateTime.of(2019, 11, 15, 8, 55), feedItemPreview.getDatePosted());
        assertEquals("300098", feedItemPreview.getUserProfilePreview().getId());
        assertEquals("buzzinga77", feedItemPreview.getUserProfilePreview().getUsername());
    }

    @Test
    void toFeedPreviewList() {
        List<VideoPreview> videoPreviewList = Arrays.asList(new VideoPreview(1, "", "", 1L, 1L, null, 1L, ""),
                new VideoPreview(1, "", "", 1L, 1L, null, 1L, ""));
        assertEquals(2, videoFeedPreviewMapper.toFeedPreviewList(videoPreviewList).size());
    }
}