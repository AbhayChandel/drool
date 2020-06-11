package com.hexlindia.drool.collection.business.api;

import com.hexlindia.drool.collection.dto.CollectionPostDto;

public interface Collection {

    CollectionPostDto createCollection(CollectionPostDto collectionPostDto);

    boolean addPost(CollectionPostDto collectionPostDto);
}
