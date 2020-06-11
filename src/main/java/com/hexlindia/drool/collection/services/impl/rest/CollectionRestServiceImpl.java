package com.hexlindia.drool.collection.services.impl.rest;

import com.hexlindia.drool.collection.business.api.Collection;
import com.hexlindia.drool.collection.dto.CollectionPostDto;
import com.hexlindia.drool.collection.services.api.rest.CollectionRestService;
import com.hexlindia.drool.collection.services.validation.CollectionPostValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CollectionRestServiceImpl implements CollectionRestService {

    private final Collection collection;

    @Override
    public ResponseEntity<Boolean> addPost(@Validated(CollectionPostValidation.class) CollectionPostDto collectionPostDto) {
        return ResponseEntity.ok(collection.addPost(collectionPostDto));
    }
}
