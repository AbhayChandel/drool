package com.hexlindia.drool.video2.data.repository.impl;

import com.hexlindia.drool.video2.view.VideoPreview;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Import(VideoViewRepositoryImpl.class)
class VideoViewRepositoryImplTest {

    @Autowired
    VideoViewRepositoryImpl videoViewRepository;

    @Test
    void getVideoPreviews() {
        List<VideoPreview> videoPreviews = videoViewRepository.getVideoPreviews(Arrays.asList(1000001, 1000002));
        assertEquals(2, videoPreviews.size());
        VideoPreview videoPreview = null;
        for (VideoPreview videoPreview1 : videoPreviews) {
            if (videoPreview1.getId().equalsIgnoreCase("1000001")) {
                videoPreview = videoPreview1;
                break;
            }
        }
        assertNotNull(videoPreview);
        assertEquals("1000001", videoPreview.getId());
        assertEquals("How to pick the right shade for your skin tone", videoPreview.getTitle());
        assertEquals("3", videoPreview.getLikes());
        assertEquals("3", videoPreview.getComments());
        assertEquals("1", videoPreview.getUserProfilePreview().getId());
        assertEquals("Priyankalove", videoPreview.getUserProfilePreview().getUsername());
    }
}