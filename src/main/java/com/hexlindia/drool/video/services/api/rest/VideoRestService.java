package com.hexlindia.drool.video.services.api.rest;

import com.hexlindia.drool.video.dto.VideoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/${rest.uri.version}/video")
public interface VideoRestService {

    @PostMapping("/insert")
    ResponseEntity<VideoDto> insert(@RequestBody VideoDto videoDto);

}
