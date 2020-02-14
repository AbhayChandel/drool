package com.hexlindia.drool.video.services.impl.rest;

import com.hexlindia.drool.video.business.api.usecase.Video;
import com.hexlindia.drool.video.dto.VideoDto;
import com.hexlindia.drool.video.services.api.rest.VideoRestService;
import com.hexlindia.drool.video.services.validation.VideoInsertValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VideoRestServiceImpl implements VideoRestService {

    private final Video video;

    @Autowired
    public VideoRestServiceImpl(Video video) {
        this.video = video;
    }

    @Override
    public ResponseEntity<VideoDto> insert(@Validated(VideoInsertValidation.class) VideoDto videoDto) {
        return ResponseEntity.ok(video.insert(videoDto));
    }
}
