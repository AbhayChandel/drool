package com.hexlindia.drool.collection.services.api.rest;

import com.hexlindia.drool.collection.view.CollectionView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/${rest.uri.version}/view/collection")
public interface CollectionViewRestService {

    @GetMapping("/find/id/{id}")
    ResponseEntity<CollectionView> findById(@PathVariable("id") int id);
}
