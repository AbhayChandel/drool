package com.hexlindia.drool.post.business.impl;

import com.hexlindia.drool.common.data.constant.PostFormat;
import com.hexlindia.drool.post.business.api.Post;
import com.hexlindia.drool.post.business.exception.PostNotFoundException;
import com.hexlindia.drool.post.data.entity.ArticleEntity;
import com.hexlindia.drool.post.data.entity.PostEntity;
import com.hexlindia.drool.post.data.repository.api.PostRepository;
import com.hexlindia.drool.post.dto.PostDto;
import com.hexlindia.drool.post.dto.mapper.PostDtoArticleEntityMapper;
import com.hexlindia.drool.post.dto.mapper.PostDtoVideoEntityMapper;
import com.hexlindia.drool.video.data.entity.VideoEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class PostImpl implements Post {

    private final PostRepository postRepository;
    private final PostDtoArticleEntityMapper postDtoArticleEntityMapper;
    private final PostDtoVideoEntityMapper postDtoVideoEntityMapper;

    @Override
    public PostDto saveOrUpdate(PostDto postDto) {
        if (postDto.getId() != null) {
            update(postDto);
        } else {
            insert(postDto);
        }
        return null;
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

    PostDto insert(PostDto postDto) {
        if (postDto.getPostFormat().equals(PostFormat.article)) {
            return saveArticleEntity(postDtoArticleEntityMapper.toEntity(postDto));
        } else if (postDto.getPostFormat().equals(PostFormat.video)) {
            return saveVideoEntity(postDtoVideoEntityMapper.toEntity(postDto));
        }
        return null;
    }

    PostEntity findPost(PostDto postDto) {
        Optional<PostEntity> postEntityOptional = postRepository.findById(Long.valueOf(postDto.getId()));
        if (!postEntityOptional.isPresent()) {
            log.warn("Post with Id : " + postDto.getId() + " not found");
            throw new PostNotFoundException("Post with Id : " + postDto.getId() + " not found");
        }
        return postEntityOptional.get();
    }

    PostDto updateAndSaveArticleEntity(PostDto postDto, ArticleEntity articleEntity) {
        articleEntity.setTitle(postDto.getTitle());
        articleEntity.setText(postDto.getText());
        articleEntity.setCoverPicture(postDto.getCoverPicture());
        return saveArticleEntity(articleEntity);
    }

    PostDto saveArticleEntity(ArticleEntity articleEntity) {
        articleEntity = postRepository.save(articleEntity);
        return postDtoArticleEntityMapper.toDto(articleEntity);
    }

    PostDto updateAndSaveVideoEntity(PostDto postDto, VideoEntity videoEntity) {
        videoEntity.setTitle(postDto.getTitle());
        videoEntity.setText(postDto.getText());
        return saveVideoEntity(videoEntity);
    }

    PostDto saveVideoEntity(VideoEntity videoEntity) {
        videoEntity = postRepository.save(videoEntity);
        return postDtoVideoEntityMapper.toDto(videoEntity);
    }
}
