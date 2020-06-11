package com.hexlindia.drool.post.data.repository.api;

import com.hexlindia.drool.common.constant.PostType2;
import com.hexlindia.drool.post.view.PostPageView;

import java.util.Optional;


public interface PostViewRepository {

    Optional<PostPageView> getPost(long id, PostType2 postType);
}
