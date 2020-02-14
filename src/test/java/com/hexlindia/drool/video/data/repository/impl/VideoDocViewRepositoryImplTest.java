package com.hexlindia.drool.video.data.repository.impl;

import com.hexlindia.drool.video.data.repository.api.VideoViewRepository;
import com.hexlindia.drool.video.view.VideoCardView;
import com.hexlindia.drool.video.view.VideoCommentCardView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class VideoDocViewRepositoryImplTest {

    @Autowired
    VideoViewRepository videoViewRepository;

    @Test
    void getVideoCardView() {
        VideoCardView videoCardView = videoViewRepository.getVideoCardView(1L);
        assertNotNull(videoCardView);
        assertEquals("1", videoCardView.getVideoView().getVideoId());
        assertEquals("vdg", videoCardView.getVideoView().getPostType());
        assertEquals("Reviewed Lakme 9to5 lipcolor", videoCardView.getVideoView().getTitle());
        assertEquals("1", videoCardView.getVideoView().getUserId());
        assertEquals("M7lc1UVf-VE", videoCardView.getVideoView().getSourceVideoId());
        assertEquals("I have tried to swatch all the shades of 9to5 lipcolor", videoCardView.getVideoView().getDescription());
        assertEquals("1.4k", videoCardView.getVideoView().getLikes());
        assertEquals("245.6k", videoCardView.getVideoView().getViews());
        assertEquals("2", videoCardView.getVideoView().getCommentCount());
        assertEquals("priyanka11", videoCardView.getUserProfileCardView().getUsername());

    }

    @Test
    void getVideoCommentCardViews() {
        List<VideoCommentCardView> videoCommentCardViews = videoViewRepository.getVideoCommentCardViews(1L);
        assertNotNull(videoCommentCardViews);
        assertEquals(2, videoCommentCardViews.size());
        VideoCommentCardView videoCommentCardView = videoCommentCardViews.get(0);
        assertEquals("1", videoCommentCardView.getVideoCommentView().getVideoCommentId());
        assertEquals("Great job. I really like all your videos", videoCommentCardView.getVideoCommentView().getComment());
        assertEquals("1", videoCommentCardView.getVideoCommentView().getVideoId());
        assertEquals("2", videoCommentCardView.getVideoCommentView().getUserId());
        assertEquals("55.6k", videoCommentCardView.getVideoCommentView().getLikes());
        assertEquals("priya21", videoCommentCardView.getUserProfileCardView().getUsername());
    }
}