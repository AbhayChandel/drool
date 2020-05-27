package com.hexlindia.drool.post.business.impl;

import com.hexlindia.drool.common.data.constant.PostFormat;
import com.hexlindia.drool.post.data.entity.ArticleEntity;
import com.hexlindia.drool.post.data.repository.api.PostRepository;
import com.hexlindia.drool.post.dto.PostDto;
import com.hexlindia.drool.post.dto.mapper.PostDtoArticleEntityMapper;
import com.hexlindia.drool.post.dto.mapper.PostDtoVideoEntityMapper;
import com.hexlindia.drool.video.data.entity.VideoEntity;
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

    PostImpl articleImplSpy;

    @Mock
    PostRepository postRepositoryMock;

    @Mock
    PostDtoArticleEntityMapper postDtoArticleEntityMapperMock;

    @Mock
    PostDtoVideoEntityMapper postDtoVideoEntityMapperMock;

    @BeforeEach
    void setUp() {
        this.articleImplSpy = Mockito.spy(new PostImpl(postRepositoryMock, postDtoArticleEntityMapperMock, postDtoVideoEntityMapperMock));
    }

    @Test
    void updateArticleCallStack() {
        PostDto postDto = new PostDto();
        postDto.setId("10000012");
        when(this.postRepositoryMock.findById(any())).thenReturn(Optional.of(new ArticleEntity()));
        this.articleImplSpy.saveOrUpdate(postDto);
        verify(this.articleImplSpy, times(1)).update(any());
        verify(this.articleImplSpy, times(1)).updateAndSaveArticleEntity(any(), any());
    }

    @Test
    void insertArticleCallStack() {
        PostDto postDto = new PostDto();
        postDto.setPostFormat(PostFormat.article);
        this.articleImplSpy.saveOrUpdate(postDto);
        verify(this.articleImplSpy, times(1)).insert(any());
        verify(this.articleImplSpy, times(1)).saveArticleEntity(any());
    }

    @Test
    void updateVideoCallStack() {
        PostDto postDto = new PostDto();
        postDto.setId("10000012");
        when(this.postRepositoryMock.findById(any())).thenReturn(Optional.of(new VideoEntity()));
        this.articleImplSpy.saveOrUpdate(postDto);
        verify(this.articleImplSpy, times(1)).update(any());
        verify(this.articleImplSpy, times(1)).updateAndSaveVideoEntity(any(), any());
    }

    @Test
    void insertVideoCallStack() {
        PostDto postDto = new PostDto();
        postDto.setPostFormat(PostFormat.video);
        this.articleImplSpy.saveOrUpdate(postDto);
        verify(this.articleImplSpy, times(1)).insert(any());
        verify(this.articleImplSpy, times(1)).saveVideoEntity(any());
    }
}