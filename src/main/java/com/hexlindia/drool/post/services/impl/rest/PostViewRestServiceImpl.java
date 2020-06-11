package com.hexlindia.drool.post.services.impl.rest;

import com.hexlindia.drool.post.business.api.PostView;
import com.hexlindia.drool.post.services.api.rest.PostViewRestService;
import com.hexlindia.drool.post.view.PostPageView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostViewRestServiceImpl implements PostViewRestService {

    private final PostView postView;

    @Override
    public ResponseEntity<PostPageView> getPost(String id) {
        return ResponseEntity.ok(postView.getPost(id));
    }
}
