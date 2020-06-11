package com.hexlindia.drool.collection.services.api.rest;

import com.hexlindia.drool.collection.dto.CollectionPostDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/${rest.uri.version}/collection")
public interface CollectionRestService {

    @PostMapping("/addpost")
    ResponseEntity<Boolean> addPost(@RequestBody CollectionPostDto collectionPostDto);
}
