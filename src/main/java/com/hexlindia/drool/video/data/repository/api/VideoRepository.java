package com.hexlindia.drool.video.data.repository.api;

import com.hexlindia.drool.video.data.doc.VideoDoc;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface VideoRepository extends MongoRepository<VideoDoc, String> {

    Optional<VideoDoc> findByIdAndActiveTrue(String id);
}
