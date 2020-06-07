package com.hexlindia.drool.collection.services.impl.rest;

import com.hexlindia.drool.collection.services.api.rest.CollectionViewRestService;
import com.hexlindia.drool.collection.view.CollectionView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CollectionViewRestServiceImpl implements CollectionViewRestService {

    private final com.hexlindia.drool.collection.business.api.CollectionView collectionView;

    @Override
    public ResponseEntity<CollectionView> findById(int id) {
        return ResponseEntity.ok(collectionView.findById(id));
    }
}
