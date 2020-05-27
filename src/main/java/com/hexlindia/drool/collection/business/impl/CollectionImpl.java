package com.hexlindia.drool.collection.business.impl;

import com.hexlindia.drool.collection.business.api.Collection;
import com.hexlindia.drool.collection.business.exception.CollectionNotFoundException;
import com.hexlindia.drool.collection.data.entity.CollectionEntity;
import com.hexlindia.drool.collection.data.repository.api.CollectionRepository;
import com.hexlindia.drool.collection.dto.CollectionPostDto;
import com.hexlindia.drool.collection.dto.mapper.CollectionPostMapper;
import com.hexlindia.drool.post.business.exception.PostNotFoundException;
import com.hexlindia.drool.post.data.entity.PostEntity;
import com.hexlindia.drool.post.data.repository.api.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CollectionImpl implements Collection {

    private final CollectionRepository collectionRepository;
    private final PostRepository postRepository;
    private final CollectionPostMapper collectionPostMapper;


    @Override
    public CollectionPostDto createCollection(CollectionPostDto collectionPostDto) {
        return null;
    }

    @Override
    @Transactional
    public boolean addPost(CollectionPostDto collectionPostDto) {
        CollectionEntity collection = getCollection(collectionPostDto);
        PostEntity post = getPost(collectionPostDto.getPostId());
        collection.addPost(post);
        collectionRepository.save(collection);
        return true;
        //TODO
        // Remove this after confirming with implementation at client application.
        //return collectionPostMapper.toDto(collection);
    }

    CollectionEntity getCollection(CollectionPostDto collectionPostDto) {
        CollectionEntity collection = null;
        if (collectionPostDto.getCollectionId() != null) {
            collection = getCollectionFromRepository(collectionPostDto.getCollectionId());
        } else {
            collection = collectionRepository.save(collectionPostMapper.toEntity(collectionPostDto));
        }
        return collection;
    }

    CollectionEntity getCollectionFromRepository(String collectionId) {
        Optional<CollectionEntity> collection = collectionRepository.findById(Integer.valueOf(collectionId));
        if (collection.isPresent()) {
            return collection.get();
        }
        log.error("Collection with id " + collectionId + " not found");
        throw new CollectionNotFoundException("Collection with id " + collectionId + " not found");
    }

    PostEntity getPost(String postId) {
        Optional<PostEntity> post = postRepository.findById(Long.valueOf(postId));
        if (post.isPresent()) {
            return post.get();
        }
        log.error("Post with id " + postId + " not found");
        throw new PostNotFoundException("Post with id " + postId + " not found");
    }


}
