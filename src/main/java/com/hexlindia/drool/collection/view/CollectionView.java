package com.hexlindia.drool.collection.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hexlindia.drool.article.view.ArticleMinimalPreview;
import com.hexlindia.drool.discussion2.view.DiscussionMinimalPreview;
import com.hexlindia.drool.video2.view.VideoMinimalPreview;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CollectionView {

    @JsonProperty("videos")
    private List<VideoMinimalPreview> videoMinimalPreviewList;

    @JsonProperty("articles")
    private List<ArticleMinimalPreview> articleMinimalPreviewList;

    @JsonProperty("discussions")
    private List<DiscussionMinimalPreview> discussionMinimalPreviewList;
}
