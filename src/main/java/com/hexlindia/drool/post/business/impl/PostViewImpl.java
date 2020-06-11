package com.hexlindia.drool.post.business.impl;

import com.hexlindia.drool.post.business.api.PostView;
import com.hexlindia.drool.post.data.repository.api.PostViewRepository;
import com.hexlindia.drool.post.view.PostPageView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PostViewImpl implements PostView {

    private final PostViewRepository postViewRepository;

    @Override
    public PostPageView getPost(String id) {
        /*Optional<PostPageView> postPageView = postViewRepository.getPost(Long.valueOf(id), );
        if (postPageView.isPresent()) {
            return postPageView.get();
        }
        throw new PostNotFoundException("Post with id " + id + " not found");*/
        return null;
    }
}
