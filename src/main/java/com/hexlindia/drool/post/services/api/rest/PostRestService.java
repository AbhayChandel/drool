package com.hexlindia.drool.post.services.api.rest;

import com.hexlindia.drool.post.dto.PostDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/${rest.uri.version}/post")
public interface PostRestService {

    @PostMapping("/save")
    ResponseEntity<PostDto> insertOrUpdate(@RequestBody PostDto postDto);
}
