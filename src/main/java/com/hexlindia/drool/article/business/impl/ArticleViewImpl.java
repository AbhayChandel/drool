package com.hexlindia.drool.article.business.impl;

import com.hexlindia.drool.article.business.api.ArticleView;
import com.hexlindia.drool.article.data.repository.api.ArticleViewRepository;
import com.hexlindia.drool.article.view.ArticlePreview;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ArticleViewImpl implements ArticleView {

    private final ArticleViewRepository articleViewRepository;

    @Override
    public List<ArticlePreview> getArticlePreviews(List<Integer> idList) {
        return articleViewRepository.getArticlePreviews(idList);
    }
}
