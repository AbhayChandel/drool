package com.hexlindia.drool.article.business.api;

import com.hexlindia.drool.article.view.ArticlePreview;

import java.util.List;

public interface ArticleView {

    List<ArticlePreview> getArticlePreviews(List<Integer> idList);
}
