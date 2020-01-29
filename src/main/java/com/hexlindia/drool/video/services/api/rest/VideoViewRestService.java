package com.hexlindia.drool.video.services.api.rest;

import com.hexlindia.drool.video.view.VideoCardView;
import com.hexlindia.drool.video.view.VideoPageView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/${rest.uri.version}/view/video")
public interface VideoViewRestService {

    @GetMapping(value = "/card/id/{id}")
    ResponseEntity<VideoCardView> findVideoCardViewById(@PathVariable("id") Long id);

    @GetMapping(value = "/page/id/{id}")
    ResponseEntity<VideoPageView> findVideoPageViewById(@PathVariable("id") Long id);
}
