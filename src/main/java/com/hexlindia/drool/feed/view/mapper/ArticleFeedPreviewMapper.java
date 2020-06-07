package com.hexlindia.drool.feed.view.mapper;

import com.hexlindia.drool.article.view.ArticlePreview;
import com.hexlindia.drool.feed.view.FeedItemPreview;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ArticleFeedPreviewMapper {


    public abstract FeedItemPreview toFeedPreview(ArticlePreview articlePreview);

    public abstract List<FeedItemPreview> toFeedPreviewList(List<ArticlePreview> articlePreviewList);

    @AfterMapping
    protected void afterMappingToDto(@MappingTarget FeedItemPreview feedItemPreview) {
        feedItemPreview.setItemType("article");
    }
}
