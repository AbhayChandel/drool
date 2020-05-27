package com.hexlindia.drool.post.business.api;

import com.hexlindia.drool.post.dto.PostDto;

public interface Post {

    PostDto saveOrUpdate(PostDto postDto);
}
