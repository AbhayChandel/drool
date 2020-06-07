package com.hexlindia.drool.article.data.repository.impl;

import com.hexlindia.drool.article.view.ArticlePreview;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Import(ArticleViewRepositoryImpl.class)
class ArticleViewRepositoryImplTest {

    @Autowired
    ArticleViewRepositoryImpl articleViewRepository;

    @Test
    void getArticlePreviews() {
        List<ArticlePreview> articlePreviews = articleViewRepository.getArticlePreviews(Arrays.asList(2000001, 2000002));
        assertEquals(2, articlePreviews.size());
        ArticlePreview articlePreview = null;
        for (ArticlePreview articlePreview1 : articlePreviews) {
            if (articlePreview1.getId().equalsIgnoreCase("2000001")) {
                articlePreview = articlePreview1;
                break;
            }
        }
        assertNotNull(articlePreview);
        assertEquals("2000001", articlePreview.getId());
        assertEquals("My favorite lipsticks for th fall", articlePreview.getTitle());
        assertEquals("2", articlePreview.getLikes());
        assertEquals("4", articlePreview.getComments());
        assertEquals("24, May 2019", articlePreview.getDatePosted());
        assertEquals("2", articlePreview.getUserProfilePreview().getId());
        assertEquals("priyankasingh", articlePreview.getUserProfilePreview().getUsername());
    }
}