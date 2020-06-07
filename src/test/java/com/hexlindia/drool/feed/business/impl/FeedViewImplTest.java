package com.hexlindia.drool.feed.business.impl;

import com.hexlindia.drool.article.business.api.ArticleView;
import com.hexlindia.drool.discussion2.business.api.DiscussionView;
import com.hexlindia.drool.feed.data.entity.FeedEntity;
import com.hexlindia.drool.feed.data.entity.FeedEntityId;
import com.hexlindia.drool.feed.data.repository.api.FeedRepository;
import com.hexlindia.drool.feed.view.mapper.ArticleFeedPreviewMapper;
import com.hexlindia.drool.feed.view.mapper.VideoFeedPreviewMapper;
import com.hexlindia.drool.video2.business.api.VideoView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class FeedViewImplTest {

    FeedViewImpl feedViewSpy;

    @MockBean
    FeedRepository feedRepositoryMock;

    @MockBean
    ArticleView articleViewMock;

    @MockBean
    ArticleFeedPreviewMapper articleFeedPreviewMapperMock;

    @MockBean
    VideoView videoViewMock;

    @MockBean
    VideoFeedPreviewMapper videoFeedPreviewMapperMock;

    @MockBean
    DiscussionView discussionViewMock;

    @BeforeEach
    void setUp() {
        feedViewSpy = spy(new FeedViewImpl(feedRepositoryMock, articleViewMock, articleFeedPreviewMapperMock, videoViewMock, videoFeedPreviewMapperMock, discussionViewMock));
    }

    @Test
    void getFeedPage_feed_items_not_found() {
        when(feedRepositoryMock.findAll((Pageable) any())).thenReturn(Page.empty());
        assertTrue(feedViewSpy.getFeedPage(0, 1).isEmpty());
    }

    @Test
    void getFeedPage__feed_items_found() {
        Page<FeedEntity> feedItems = new PageImpl<>(Arrays.asList(new FeedEntity(new FeedEntityId(1, 1), null)));
        when(feedRepositoryMock.findAll((Pageable) any())).thenReturn(feedItems);
        doReturn(null).when(feedViewSpy).getFeedItemPreviews(any());
        feedViewSpy.getFeedPage(0, 1);
        verify(feedViewSpy, times(1)).getFeedItemPreviews(any());
    }

    @Test
    void getFeedItemPreviews() {
        Map<Integer, Set<Integer>> itemGroupsMapMock = new HashMap<>();
        Set<Integer> group1 = new HashSet<>();
        group1.add(1);
        group1.add(2);
        Set<Integer> group2 = new HashSet<>();
        group2.add(1);
        group2.add(2);
        Set<Integer> group3 = new HashSet<>();
        group3.add(1);
        itemGroupsMapMock.put(1, group1);
        itemGroupsMapMock.put(2, group2);
        itemGroupsMapMock.put(3, group3);
        doReturn(itemGroupsMapMock).when(feedViewSpy).getItemGroupMap(any());
        feedViewSpy.getFeedItemPreviews(null);
        verify(feedViewSpy, times(1)).getVideoItems(any());
        verify(feedViewSpy, times(1)).getArticleItems(any());
        verify(feedViewSpy, times(1)).getDiscussionItems(any());
    }

    @Test
    void getItemGroupMap() {
        Page<FeedEntity> feedItems = new PageImpl<>(Arrays.asList(new FeedEntity(new FeedEntityId(1, 1), null),
                new FeedEntity(new FeedEntityId(2, 1), null),
                new FeedEntity(new FeedEntityId(1, 2), null),
                new FeedEntity(new FeedEntityId(1, 3), null)));

        Map<Integer, Set<Integer>> itemGroups = feedViewSpy.getItemGroupMap(feedItems);
        assertEquals(2, itemGroups.get(1).size());
        assertEquals(1, itemGroups.get(2).size());
        assertEquals(1, itemGroups.get(3).size());
    }
}