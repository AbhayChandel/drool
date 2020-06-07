package com.hexlindia.drool.feed.view.mapper;

import com.hexlindia.drool.article.view.ArticlePreview;
import com.hexlindia.drool.feed.view.FeedItemPreview;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ArticleFeedPreviewMapperTest {

    @Autowired
    ArticleFeedPreviewMapper articleFeedPreviewMapper;

    @Test
    void toFeedPreview() {
        ArticlePreview articlePreview = new ArticlePreview(10000001, "This is an article to test mapper", 1222L,
                556L, 100004L, "stylo");
        FeedItemPreview feedItemPreview = articleFeedPreviewMapper.toFeedPreview(articlePreview);
        assertEquals("10000001", feedItemPreview.getId());
        assertEquals("article", feedItemPreview.getItemType());
        assertEquals("This is an article to test mapper", feedItemPreview.getTitle());
        assertEquals("1.2k", feedItemPreview.getLikes());
        assertEquals("556", feedItemPreview.getComments());
        assertEquals("100004", feedItemPreview.getUserProfilePreview().getId());
        assertEquals("stylo", feedItemPreview.getUserProfilePreview().getUsername());
    }

    @Test
    void toFeedPreviewList() {
        List<ArticlePreview> articlePreviewList = Arrays.asList(new ArticlePreview(1, "", 1L, 1L, 1L, ""),
                new ArticlePreview(1, "", 1L, 1L, 1L, ""),
                new ArticlePreview(1, "", 1L, 1L, 1L, ""));
        assertEquals(3, articleFeedPreviewMapper.toFeedPreviewList(articlePreviewList).size());
    }
}