package com.hexlindia.drool.post.data.repository.api;

import com.hexlindia.drool.post.dto.PostDto;

public interface ArticleViewRepository {

    PostDto getArticle(long id);
}
