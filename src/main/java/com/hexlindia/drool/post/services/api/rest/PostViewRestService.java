package com.hexlindia.drool.post.services.api.rest;

import com.hexlindia.drool.post.view.PostPageView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/${rest.uri.version}/view/post")
public interface PostViewRestService {

    @GetMapping("/page/id/{id}")
    ResponseEntity<PostPageView> getPost(@PathVariable("id") String id);
}
