package com.hexlindia.drool.video.services.impl.rest;

import com.hexlindia.drool.video.business.api.usecase.VideoView;
import com.hexlindia.drool.video.services.api.rest.VideoViewRestService;
import com.hexlindia.drool.video.view.VideoCardView;
import com.hexlindia.drool.video.view.VideoPageView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VideoViewRestServiceImpl implements VideoViewRestService {

    private final VideoView videoView;

    public VideoViewRestServiceImpl(VideoView videoView) {
        this.videoView = videoView;
    }

    @Override
    public ResponseEntity<VideoCardView> findVideoCardViewById(Long id) {
        return ResponseEntity.ok(this.videoView.getVideoCardView(id));
    }

    @Override
    public ResponseEntity<VideoPageView> findVideoPageViewById(Long id) {
        return ResponseEntity.ok(this.videoView.getVideoPageView(id));
    }
}
