package com.hexlindia.drool.collection.data.repository.api;

import com.hexlindia.drool.collection.data.entity.CollectionEntity2;
import com.hexlindia.drool.video2.data.entity.VideoEntity2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class CollectionRepository2Test {

    @Autowired
    private CollectionRepository2 collectionRepository2;

    @Test
    void saveVideo() {

        Optional<CollectionEntity2> collectionEntity2 = collectionRepository2.findById(1001);
        assertTrue(collectionEntity2.isPresent());
        VideoEntity2 video = new VideoEntity2();
        CollectionEntity2 collectionEntity21 = collectionEntity2.get();
        collectionEntity21.addVideo(video);
        collectionRepository2.save(collectionEntity21);
    }

}