package com.hexlindia.drool.video.data.repository.impl;

import com.hexlindia.drool.video.data.doc.ProductRef;
import com.hexlindia.drool.video.data.doc.UserRef;
import com.hexlindia.drool.video.data.doc.VideoDoc;
import com.hexlindia.drool.video.data.repository.api.VideoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest
public class VideoDocRepositoryTest {

    private final VideoRepository videoRepository;

    private final MongoTemplate mongoTemplate;

    private String populatedActiveVideoId = "";
    private String populatedInactiveVideoId = "";

    @Autowired
    public VideoDocRepositoryTest(VideoRepository videoRepository, MongoTemplate mongoTemplate) {
        this.videoRepository = videoRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Test
    public void test_Insert() {


        VideoDoc videoDoc = new VideoDoc("guide", "This is a test video entry", "This video is inserted as part of testing with MongoDB", "vQ765gh",
                new ProductRef("abc", "Lakme 9to5 Lipcolor", "lipcolor"),
                new UserRef("123", "shabana"));
        videoDoc = videoRepository.insert(videoDoc);
        Assert.notNull(videoDoc.getId(), "");
    }

    @Test
    public void test_findByIdActiveTrue() {
        assertTrue(videoRepository.findByIdAndActiveTrue(populatedActiveVideoId).isPresent());
    }

    @Test
    public void test_findByIdActiveFalse() {
        assertFalse(videoRepository.findByIdAndActiveTrue(populatedInactiveVideoId).isPresent());
    }

    @BeforeEach
    public void setUp() {
        VideoDoc videoDocActive = new VideoDoc("guide", "This video will be prepoulated for testing", "This video is inserted as part of testing with MongoDB", "vQ765gh",
                new ProductRef("abc", "Lakme 9to5 Lipcolor", "lipcolor"),
                new UserRef("123", "shabana"));

        VideoDoc videoDocInactive = new VideoDoc("guide", "This video will be prepoulated for testing", "This video is inserted as part of testing with MongoDB", "vQ765gh",
                new ProductRef("abc", "Lakme 9to5 Lipcolor", "lipcolor"),
                new UserRef("123", "shabana"));
        videoDocInactive.setActive(false);
        populatedActiveVideoId = this.mongoTemplate.insert(videoDocActive).getId();
        populatedInactiveVideoId = this.mongoTemplate.insert(videoDocInactive).getId();

    }
}
