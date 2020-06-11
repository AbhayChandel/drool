package com.hexlindia.drool.video.services.impl.rest;

import com.hexlindia.drool.video.business.api.Video;
import com.hexlindia.drool.video.dto.VideoDtoMOngo;
import com.hexlindia.drool.video.services.api.rest.VideoViewsRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VideoViewsRestServiceImpl implements VideoViewsRestService {

    private final Video video;

    @Autowired
    public VideoViewsRestServiceImpl(Video video) {
        this.video = video;
    }

    @Override
    public ResponseEntity<VideoDtoMOngo> findById(String id) {
        return ResponseEntity.ok(video.findById(id));
    }
}
