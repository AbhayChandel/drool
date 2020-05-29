package com.hexlindia.drool.post.services.impl.rest;

import com.hexlindia.drool.post.business.api.Post;
import com.hexlindia.drool.post.dto.PostDto;
import com.hexlindia.drool.post.services.api.rest.PostRestService;
import com.hexlindia.drool.post.services.validation.PostValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostRestServiceImpl implements PostRestService {

    private final Post post;

    @Override
    public ResponseEntity<PostDto> insertOrUpdate(@Validated(PostValidation.class) PostDto postDto) {
        return ResponseEntity.ok(post.insertOrUpdate(postDto));
    }
}
