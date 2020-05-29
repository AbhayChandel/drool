package com.hexlindia.drool.post.data.repository.api;

import com.hexlindia.drool.post.view.PostPageView;

import java.util.Optional;


public interface PostViewRepository {

    Optional<PostPageView> getPost(long id);
}
