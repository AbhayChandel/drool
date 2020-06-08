package com.hexlindia.drool.collection.data.repository.api;

import com.hexlindia.drool.article.data.entity.ArticleEntity2;
import com.hexlindia.drool.collection.data.entity.CollectionEntity2;
import com.hexlindia.drool.discussion2.data.entity.DiscussionEntity2;
import com.hexlindia.drool.video2.data.entity.VideoEntity2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class CollectionRepository2Test {

    @Autowired
    private CollectionRepository2 collectionRepository2;

    @Autowired
    EntityManager em;

    @Test
    void saveVideo() {
        CollectionEntity2 collectionEntity2 = em.find(CollectionEntity2.class, 1001);
        assertNotNull(collectionEntity2);
        VideoEntity2 videoEntity2 = em.find(VideoEntity2.class, 1000003);
        assertNotNull(videoEntity2);
        collectionEntity2.addVideo(videoEntity2);
        collectionRepository2.save(collectionEntity2);
        collectionEntity2 = em.find(CollectionEntity2.class, 1001);
        assertEquals(3, collectionEntity2.getPosts().getVideo().size());
    }

    @Test
    void saveArticle() {
        CollectionEntity2 collectionEntity2 = em.find(CollectionEntity2.class, 1001);
        assertNotNull(collectionEntity2);
        ArticleEntity2 articleEntity2 = em.find(ArticleEntity2.class, 2000003);
        assertNotNull(articleEntity2);
        collectionEntity2.addArticle(articleEntity2);
        collectionRepository2.save(collectionEntity2);
        collectionEntity2 = em.find(CollectionEntity2.class, 1001);
        assertEquals(3, collectionEntity2.getPosts().getArticle().size());
    }

    @Test
    void saveDiscussion() {
        CollectionEntity2 collectionEntity2 = em.find(CollectionEntity2.class, 1001);
        assertNotNull(collectionEntity2);
        DiscussionEntity2 discussionEntity2 = em.find(DiscussionEntity2.class, 40003);
        assertNotNull(discussionEntity2);
        collectionEntity2.addDiscussion(discussionEntity2);
        collectionRepository2.save(collectionEntity2);
        collectionEntity2 = em.find(CollectionEntity2.class, 1001);
        assertEquals(3, collectionEntity2.getPosts().getDiscussion().size());
    }

}