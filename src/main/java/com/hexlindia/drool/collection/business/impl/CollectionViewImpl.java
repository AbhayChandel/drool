package com.hexlindia.drool.collection.business.impl;

import com.hexlindia.drool.collection.data.repository.api.CollectionViewRepository;
import com.hexlindia.drool.collection.view.CollectionView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CollectionViewImpl implements com.hexlindia.drool.collection.business.api.CollectionView {

    private final CollectionViewRepository collectionViewRepository;

    @Override
    public CollectionView findById(int id) {
        int pageSize = 5;
        return new CollectionView(collectionViewRepository.getVideos(id, 0, pageSize), collectionViewRepository.getArticles(id, 0, pageSize), collectionViewRepository.getDiscussions(id, 0, pageSize));
    }
}
