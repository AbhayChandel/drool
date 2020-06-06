package com.hexlindia.drool.collection.business.impl;

import com.hexlindia.drool.article.data.entity.ArticleEntity2;
import com.hexlindia.drool.article.data.repository.api.ArticleRepository2;
import com.hexlindia.drool.article.exception.ArticleNotFoundException;
import com.hexlindia.drool.collection.business.api.Collection;
import com.hexlindia.drool.collection.business.exception.CollectionNotFoundException;
import com.hexlindia.drool.collection.data.entity.CollectionEntity2;
import com.hexlindia.drool.collection.data.repository.api.CollectionRepository2;
import com.hexlindia.drool.collection.dto.CollectionPostDto;
import com.hexlindia.drool.collection.dto.mapper.CollectionPostMapper;
import com.hexlindia.drool.common.constant.PostType2;
import com.hexlindia.drool.post.data.repository.api.PostRepository;
import com.hexlindia.drool.video.exception.VideoNotFoundException;
import com.hexlindia.drool.video2.data.entity.VideoEntity2;
import com.hexlindia.drool.video2.data.repository.api.VideoRepository2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CollectionImpl implements Collection {

    private final CollectionRepository2 collectionRepository;
    private final PostRepository postRepository;
    private final CollectionPostMapper collectionPostMapper;
    private final VideoRepository2 videoRepository2;
    private final ArticleRepository2 articleRepository2;


    @Override
    public CollectionPostDto createCollection(CollectionPostDto collectionPostDto) {
        return null;
    }

    @Override
    @Transactional
    public boolean addPost(CollectionPostDto collectionPostDto) {
        CollectionEntity2 collection = getCollection(collectionPostDto);
        setPostInCollection(collection, collectionPostDto.getPostType(), collectionPostDto.getPostId());
        collectionRepository.save(collection);
        return true;
        //TODO
        // Remove this after confirming with implementation at client application.
        //return collectionPostMapper.toDto(collection);
    }

    CollectionEntity2 getCollection(CollectionPostDto collectionPostDto) {
        CollectionEntity2 collection = null;
        if (collectionPostDto.getCollectionId() != null) {
            collection = getCollectionFromRepository(collectionPostDto.getCollectionId());
        } else {
            //collection = collectionRepository.save(collectionPostMapper.toEntity(collectionPostDto));
        }
        return collection;
    }

    CollectionEntity2 getCollectionFromRepository(String collectionId) {
        Optional<CollectionEntity2> collection = collectionRepository.findById(Integer.valueOf(collectionId));
        if (collection.isPresent()) {
            return collection.get();
        }
        log.error("Collection with id " + collectionId + " not found");
        throw new CollectionNotFoundException("Collection with id " + collectionId + " not found");
    }

    void setPostInCollection(CollectionEntity2 collection, PostType2 postType, String postId) {
        if (postType.equals(PostType2.VIDEO)) {
            addVideoPost(collection, postId);
        } else if (postType.equals(PostType2.ARTICLE)) {
            addArticlePost(collection, postId);
        } else if (postType.equals(PostType2.DISCUSSION)) {

        }
    }

    void addVideoPost(CollectionEntity2 collection, String videoId) {
        Optional<VideoEntity2> video = videoRepository2.findById(Integer.valueOf(videoId));
        if (video.isPresent()) {
            collection.addVideo(video.get());
        }
        log.error("Video with id " + videoId + " not found");
        throw new VideoNotFoundException("Video with id " + videoId + " not found");
    }

    void addArticlePost(CollectionEntity2 collection, String articleId) {
        Optional<ArticleEntity2> article = articleRepository2.findById(Integer.valueOf(articleId));
        if (article.isPresent()) {
            collection.addArticle(article.get());
        }
        log.error("Article with id " + articleId + " not found");
        throw new ArticleNotFoundException("Article with id " + articleId + " not found");
    }


}
