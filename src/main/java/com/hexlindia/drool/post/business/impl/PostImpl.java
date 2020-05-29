package com.hexlindia.drool.post.business.impl;

import com.hexlindia.drool.common.constant.PostType2;
import com.hexlindia.drool.post.business.api.Post;
import com.hexlindia.drool.post.business.exception.PostNotFoundException;
import com.hexlindia.drool.post.data.entity.ArticleEntity;
import com.hexlindia.drool.post.data.entity.PostEntity;
import com.hexlindia.drool.post.data.entity.VideoEntity;
import com.hexlindia.drool.post.data.repository.api.PostRepository;
import com.hexlindia.drool.post.dto.PostDto;
import com.hexlindia.drool.post.dto.mapper.PostDtoArticleEntityMapper;
import com.hexlindia.drool.post.dto.mapper.PostDtoVideoEntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class PostImpl implements Post {

    private final PostRepository postRepository;
    private final PostDtoArticleEntityMapper postDtoArticleEntityMapper;
    private final PostDtoVideoEntityMapper postDtoVideoEntityMapper;

    @Override
    @Transactional
    public PostDto insertOrUpdate(PostDto postDto) {
        if (postDto.getId() != null) {
            return update(postDto);
        } else {
            return insert(postDto);
        }
    }

    PostDto update(PostDto postDto) {
        PostEntity postEntity = findPost(postDto);
        if (postEntity instanceof ArticleEntity) {
            return updateAndSaveArticleEntity(postDto, (ArticleEntity) postEntity);
        } else if (postEntity instanceof VideoEntity) {
            return updateAndSaveVideoEntity(postDto, (VideoEntity) postEntity);
        }
        return null;
    }

    PostEntity findPost(PostDto postDto) {
        Optional<PostEntity> postEntityOptional = postRepository.findById(Long.valueOf(postDto.getId()));
        if (postEntityOptional.isPresent()) {
            return postEntityOptional.get();
        }
        log.warn("Post with Id : " + postDto.getId() + " not found");
        throw new PostNotFoundException("Post with Id : " + postDto.getId() + " not found");
    }

    PostDto updateAndSaveArticleEntity(PostDto postDto, ArticleEntity articleEntity) {
        if (postDto.getTitle() != null && !postDto.getTitle().isEmpty()) {
            articleEntity.setTitle(postDto.getTitle());
        }
        articleEntity.setText(postDto.getText());
        articleEntity.setCoverPicture(postDto.getCoverPicture());
        return saveArticleEntity(articleEntity);
    }

    PostDto updateAndSaveVideoEntity(PostDto postDto, VideoEntity videoEntity) {
        if (postDto.getTitle() != null && !postDto.getTitle().isEmpty()) {
            videoEntity.setTitle(postDto.getTitle());
        }
        videoEntity.setText(postDto.getText());
        return saveVideoEntity(videoEntity);
    }

    PostDto insert(PostDto postDto) {
        if (postDto.getType().equals(PostType2.ARTICLE)) {
            ArticleEntity articleEntity = postDtoArticleEntityMapper.toEntity(postDto);
            setDefaultInsertValues(articleEntity);
            return saveArticleEntity(articleEntity);
        } else if (postDto.getType().equals(PostType2.VIDEO)) {
            VideoEntity videoEntity = postDtoVideoEntityMapper.toEntity(postDto);
            setDefaultInsertValues(videoEntity);
            return saveVideoEntity(videoEntity);
        }
        return null;
    }

    PostDto saveArticleEntity(ArticleEntity articleEntity) {
        articleEntity = postRepository.save(articleEntity);
        return postDtoArticleEntityMapper.toDto(articleEntity);
    }

    PostDto saveVideoEntity(VideoEntity videoEntity) {
        videoEntity = postRepository.save(videoEntity);
        return postDtoVideoEntityMapper.toDto(videoEntity);
    }

    void setDefaultInsertValues(PostEntity postEntity) {
        postEntity.setDatePosted(LocalDateTime.now());
        postEntity.setActive(true);
    }
}
