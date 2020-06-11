package com.hexlindia.drool.collection.business.impl;

import com.hexlindia.drool.article.data.entity.ArticleEntity2;
import com.hexlindia.drool.article.data.repository.api.ArticleRepository2;
import com.hexlindia.drool.article.exception.ArticleNotFoundException;
import com.hexlindia.drool.collection.business.exception.CollectionNotFoundException;
import com.hexlindia.drool.collection.data.entity.CollectionEntity2;
import com.hexlindia.drool.collection.data.repository.api.CollectionRepository2;
import com.hexlindia.drool.collection.dto.CollectionPostDto;
import com.hexlindia.drool.collection.dto.mapper.CollectionPostMapper;
import com.hexlindia.drool.common.constant.PostType2;
import com.hexlindia.drool.discussion2.data.entity.DiscussionEntity2;
import com.hexlindia.drool.discussion2.data.repository.api.DiscussionRepository2;
import com.hexlindia.drool.discussion2.exception.DiscussionNotFoundException;
import com.hexlindia.drool.video.exception.VideoNotFoundException;
import com.hexlindia.drool.video2.data.entity.VideoEntity2;
import com.hexlindia.drool.video2.data.repository.api.VideoRepository2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class CollectionImplTest {

    CollectionImpl collectionSpy;

    @MockBean
    CollectionRepository2 collectionRepositoryMock;

    @MockBean
    VideoRepository2 videoRepository2Mock;

    @MockBean
    ArticleRepository2 articleRepository2Mock;

    @MockBean
    DiscussionRepository2 discussionRepository2Mock;

    @MockBean
    CollectionPostMapper collectionPostMapperMock;


    @BeforeEach
    void setUp() {
        this.collectionSpy = Mockito.spy(new CollectionImpl(collectionRepositoryMock, videoRepository2Mock, articleRepository2Mock,
                discussionRepository2Mock, collectionPostMapperMock));
    }

    @Test
    void getCollection_id_present() {
        doReturn(null).when(collectionSpy).getCollectionFromRepository(any());
        CollectionPostDto collectionPostDto = new CollectionPostDto();
        collectionPostDto.setCollectionId("1");
        collectionSpy.getCollection(collectionPostDto);
        verify(collectionSpy, times(1)).getCollectionFromRepository(any());
    }

    @Test
    void getCollection_id_not_present() {
        collectionSpy.getCollection(new CollectionPostDto());
        verify(collectionPostMapperMock, times(1)).toEntity(any());
    }

    @Test
    void getCollectionFromRepository_not_found() {
        when(collectionRepositoryMock.findById(any())).thenReturn(Optional.empty());
        assertThrows(CollectionNotFoundException.class, () -> {
            collectionSpy.getCollectionFromRepository("1");
        });
    }

    @Test
    void getCollectionFromRepository_found() {
        CollectionEntity2 collectionEntity2 = new CollectionEntity2();
        when(collectionRepositoryMock.findById(any())).thenReturn(Optional.of(collectionEntity2));
        assertEquals(collectionEntity2, collectionSpy.getCollectionFromRepository("1"));
    }

    @Test
    void setPostInCollection_for_video() {
        doNothing().when(collectionSpy).addVideoPost(any(), any());
        collectionSpy.setPostInCollection(null, PostType2.VIDEO, null);
        verify(collectionSpy, times(1)).addVideoPost(any(), any());
    }

    @Test
    void setPostInCollection_for_article() {
        doNothing().when(collectionSpy).addArticlePost(any(), any());
        collectionSpy.setPostInCollection(null, PostType2.ARTICLE, null);
        verify(collectionSpy, times(1)).addArticlePost(any(), any());
    }

    @Test
    void setPostInCollection_for_discussion() {
        doNothing().when(collectionSpy).addDiscussionPost(any(), any());
        collectionSpy.setPostInCollection(null, PostType2.DISCUSSION, null);
        verify(collectionSpy, times(1)).addDiscussionPost(any(), any());
    }

    @Test
    void addVideoPost_video_found() {
        when(videoRepository2Mock.findById(any())).thenReturn(Optional.of(new VideoEntity2()));
        CollectionEntity2 collectionEntity2Spy = spy(CollectionEntity2.class);
        doNothing().when(collectionEntity2Spy).addVideo(any());
        collectionSpy.addVideoPost(collectionEntity2Spy, "1");
        verify(collectionEntity2Spy, times(1)).addVideo(any());
    }

    @Test
    void addVideoPost_video_not_found() {
        when(videoRepository2Mock.findById(any())).thenReturn(Optional.empty());
        assertThrows(VideoNotFoundException.class, () -> {
            collectionSpy.addVideoPost(null, "1");
        });
    }

    @Test
    void addArticlePost_article_found() {
        when(articleRepository2Mock.findById(any())).thenReturn(Optional.of(new ArticleEntity2()));
        CollectionEntity2 collectionEntity2Spy = spy(CollectionEntity2.class);
        doNothing().when(collectionEntity2Spy).addArticle(any());
        collectionSpy.addArticlePost(collectionEntity2Spy, "1");
        verify(collectionEntity2Spy, times(1)).addArticle(any());
    }

    @Test
    void addArticlePost_article_not_found() {
        when(articleRepository2Mock.findById(any())).thenReturn(Optional.empty());
        assertThrows(ArticleNotFoundException.class, () -> {
            collectionSpy.addArticlePost(null, "1");
        });
    }

    @Test
    void addDiscussionPost_discussion_found() {
        when(discussionRepository2Mock.findById(any())).thenReturn(Optional.of(new DiscussionEntity2()));
        CollectionEntity2 collectionEntity2Spy = spy(CollectionEntity2.class);
        doNothing().when(collectionEntity2Spy).addDiscussion(any());
        collectionSpy.addDiscussionPost(collectionEntity2Spy, "1");
        verify(collectionEntity2Spy, times(1)).addDiscussion(any());
    }

    @Test
    void addDiscussionPost_discussion_not_found() {
        when(articleRepository2Mock.findById(any())).thenReturn(Optional.empty());
        assertThrows(DiscussionNotFoundException.class, () -> {
            collectionSpy.addDiscussionPost(null, "1");
        });
    }
}