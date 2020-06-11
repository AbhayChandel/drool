package com.hexlindia.drool.article.data.repository.api;

import com.hexlindia.drool.article.view.ArticlePreview;

import java.util.List;

public interface ArticleViewRepository {

    List<ArticlePreview> getArticlePreviews(List<Integer> ids);
}
