package com.hexlindia.drool.collection.data.repository.impl;

import com.hexlindia.drool.article.view.ArticleMinimalPreview;
import com.hexlindia.drool.discussion2.view.DiscussionMinimalPreview;
import com.hexlindia.drool.video2.view.VideoMinimalPreview;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Import(CollectionViewRepositoryImpl.class)
class CollectionViewRepositoryImplTest {

    @Autowired
    CollectionViewRepositoryImpl collectionViewRepository;

    @Test
    void getArticles() {
        List<ArticleMinimalPreview> articles = collectionViewRepository.getArticles(1001, 0, 1);
        assertEquals(1, articles.size());
    }

    @Test
    void getVideos() {
        List<VideoMinimalPreview> videos = collectionViewRepository.getVideos(1001, 0, 10);
        assertEquals(2, videos.size());
    }

    @Test
    void getDiscussions() {
        List<DiscussionMinimalPreview> discussions = collectionViewRepository.getDiscussions(1001, 0, 10);
        assertEquals(2, discussions.size());
    }
}