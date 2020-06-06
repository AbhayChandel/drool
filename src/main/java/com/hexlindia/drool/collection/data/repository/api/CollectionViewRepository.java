package com.hexlindia.drool.collection.data.repository.api;

import com.hexlindia.drool.article.view.ArticleMinimalPreview;
import com.hexlindia.drool.video2.view.VideoMinimalPreview;

import java.util.List;

public interface CollectionViewRepository {

    List<ArticleMinimalPreview> getArticles(int id, int firstResult, int resultSetSize);

    List<VideoMinimalPreview> getVideos(int id, int firstResult, int resultSetSize);
}
