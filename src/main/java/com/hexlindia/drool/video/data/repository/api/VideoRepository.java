package com.hexlindia.drool.video.data.repository.api;

import com.hexlindia.drool.video.data.doc.VideoDoc;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoRepository extends MongoRepository<VideoDoc, String> {
}
