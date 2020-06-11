package com.hexlindia.drool.post.business.impl;

import com.hexlindia.drool.common.constant.PostType2;
import com.hexlindia.drool.post.data.entity.ArticleEntity;
import com.hexlindia.drool.post.data.entity.DiscussionEntity;
import com.hexlindia.drool.post.data.entity.VideoEntity;
import com.hexlindia.drool.post.data.repository.api.PostRepository;
import com.hexlindia.drool.post.dto.PostDto;
import com.hexlindia.drool.post.dto.mapper.PostDtoArticleEntityMapper;
import com.hexlindia.drool.post.dto.mapper.PostDtoDiscussionEntityMapper;
import com.hexlindia.drool.post.dto.mapper.PostDtoVideoEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DataJpaTest
class PostImplTest {

    PostImpl postImplSpy;

    @Mock
    PostRepository postRepositoryMock;

    @Mock
    PostDtoArticleEntityMapper postDtoArticleEntityMapperMock;

    @Mock
    PostDtoVideoEntityMapper postDtoVideoEntityMapperMock;

    @Mock
    PostDtoDiscussionEntityMapper postDtoDiscussionEntityMapperMock;

    @BeforeEach
    void setUp() {
        this.postImplSpy = Mockito.spy(new PostImpl(postRepositoryMock, postDtoArticleEntityMapperMock, postDtoVideoEntityMapperMock, postDtoDiscussionEntityMapperMock));
    }

    @Test
    void updateArticleCallStack() {
        PostDto postDto = new PostDto();
        postDto.setId("10000012");
        when(this.postRepositoryMock.findById(any())).thenReturn(Optional.of(new ArticleEntity()));
        this.postImplSpy.insertOrUpdate(postDto);
        verify(this.postImplSpy, times(1)).update(any());
        verify(this.postImplSpy, times(1)).updateAndSaveArticleEntity(any(), any());
    }

    @Test
    void updateVideoCallStack() {
        PostDto postDto = new PostDto();
        postDto.setId("10000012");
        when(this.postRepositoryMock.findById(any())).thenReturn(Optional.of(new VideoEntity()));
        this.postImplSpy.insertOrUpdate(postDto);
        verify(this.postImplSpy, times(1)).update(any());
        verify(this.postImplSpy, times(1)).updateAndSaveVideoEntity(any(), any());
    }

    @Test
    void updateDiscussionCallStack() {
        PostDto postDto = new PostDto();
        postDto.setId("10000012");
        when(this.postRepositoryMock.findById(any())).thenReturn(Optional.of(new DiscussionEntity()));
        this.postImplSpy.insertOrUpdate(postDto);
        verify(this.postImplSpy, times(1)).update(any());
        verify(this.postImplSpy, times(1)).updateAndSaveDiscussionEntity(any(), any());
    }

    @Test
    void insertArticleCallStack() {
        PostDto postDto = new PostDto();
        postDto.setType(PostType2.ARTICLE);
        doNothing().when(this.postImplSpy).setDefaultInsertValues(any());
        this.postImplSpy.insertOrUpdate(postDto);
        verify(this.postImplSpy, times(1)).insert(any());
        verify(this.postImplSpy, times(1)).saveArticleEntity(any());
    }

    @Test
    void insertVideoCallStack() {
        PostDto postDto = new PostDto();
        postDto.setType(PostType2.VIDEO);
        doNothing().when(this.postImplSpy).setDefaultInsertValues(any());
        this.postImplSpy.insertOrUpdate(postDto);
        verify(this.postImplSpy, times(1)).insert(any());
        verify(this.postImplSpy, times(1)).saveVideoEntity(any());
    }

    @Test
    void insertDiscussionCallStack() {
        PostDto postDto = new PostDto();
        postDto.setType(PostType2.DISCUSSION);
        doNothing().when(this.postImplSpy).setDefaultInsertValues(any());
        this.postImplSpy.insertOrUpdate(postDto);
        verify(this.postImplSpy, times(1)).insert(any());
        verify(this.postImplSpy, times(1)).saveDiscussionEntity(any());
    }

    @Test
    void article_title_is_not_updated_with_null_or_empty() {
        ArticleEntity articleEntitySpy = Mockito.spy(new ArticleEntity());
        when(this.postRepositoryMock.save(any())).thenReturn(articleEntitySpy);
        when(this.postDtoArticleEntityMapperMock.toDto(any())).thenReturn(new PostDto());

        postImplSpy.updateAndSaveArticleEntity(new PostDto(), articleEntitySpy);
        verify(articleEntitySpy, times(0)).setTitle(any());
    }

    @Test
    void video_title_is_not_updated_with_null_or_empty() {
        VideoEntity videoEntitySpy = Mockito.spy(new VideoEntity());
        when(this.postRepositoryMock.save(any())).thenReturn(videoEntitySpy);
        when(this.postDtoVideoEntityMapperMock.toDto(any())).thenReturn(new PostDto());

        postImplSpy.updateAndSaveVideoEntity(new PostDto(), videoEntitySpy);
        verify(videoEntitySpy, times(0)).setTitle(any());
    }

    @Test
    void discussion_title_is_not_updated_with_null_or_empty() {
        DiscussionEntity discussionEntity = Mockito.spy(new DiscussionEntity());
        when(this.postRepositoryMock.save(any())).thenReturn(discussionEntity);
        when(this.postDtoDiscussionEntityMapperMock.toDto(any())).thenReturn(new PostDto());

        postImplSpy.updateAndSaveDiscussionEntity(new PostDto(), discussionEntity);
        verify(discussionEntity, times(0)).setTitle(any());
    }
}