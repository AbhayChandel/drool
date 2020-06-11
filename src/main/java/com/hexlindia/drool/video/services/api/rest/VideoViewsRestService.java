package com.hexlindia.drool.video.services.api.rest;

import com.hexlindia.drool.video.dto.VideoDtoMOngo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/${rest.uri.version}/view/video")
public interface VideoViewsRestService {

    @GetMapping("/find/id/{id}")
    ResponseEntity<VideoDtoMOngo> findById(@PathVariable("id") String id);
}
