package com.hexlindia.drool.video.data.repository.impl;

import com.hexlindia.drool.video.data.doc.ProductRef;
import com.hexlindia.drool.video.data.doc.UserRef;
import com.hexlindia.drool.video.data.doc.VideoDoc;
import com.hexlindia.drool.video.data.repository.api.VideoTemplateRepository;
import com.hexlindia.drool.video.dto.VideoLikeUnlikeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class VideoTemplateRepositoryTest {

    private final VideoTemplateRepository videoTemplateRepository;
    private final MongoTemplate mongoTemplate;

    private VideoLikeUnlikeDto activeVideo;
    private VideoLikeUnlikeDto inactiveVideo;

    @Autowired
    public VideoTemplateRepositoryTest(VideoTemplateRepository videoTemplateRepository, MongoTemplate mongoTemplate) {
        this.videoTemplateRepository = videoTemplateRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Test
    public void test_Insert() {
        ProductRef productRef1 = new ProductRef("abc", "Lakme 9to5 Lipcolor", "lipcolor");
        ProductRef productRef2 = new ProductRef("pqr", "Chambor eyeliner", "eyeliner");
        List<ProductRef> productRefList = Arrays.asList(productRef1, productRef2);
        VideoDoc videoDoc = new VideoDoc("guide", "This is a test video entry", "This video is inserted as part of testing with MongoDB", "vQ765gh",
                productRefList,
                new UserRef("123", "shabana"));
        videoDoc = videoTemplateRepository.insert(videoDoc);
        assertNotNull(videoDoc.getId());
    }

    @Test
    public void test_findByIdActiveTrue() {
        assertNotNull(videoTemplateRepository.findByIdAndActiveTrue(activeVideo.getVideoId()));
    }

    @Test
    public void test_findByIdActiveFalse() {
        assertNull(videoTemplateRepository.findByIdAndActiveTrue(inactiveVideo.getVideoId()));
    }

    @Test
    public void test_IncrementLikes() {
        videoTemplateRepository.incrementLikes(activeVideo);
        assertEquals(1, videoTemplateRepository.findByIdAndActiveTrue(activeVideo.getVideoId()).getLikes());
    }

    @Test
    public void test_DecrementLikes() {
        videoTemplateRepository.decrementLikes(activeVideo);
        assertEquals(-1, videoTemplateRepository.findByIdAndActiveTrue(activeVideo.getVideoId()).getLikes());
    }

    @BeforeEach
    public void setUp() {
        ProductRef productRef1 = new ProductRef("abc", "Lakme 9to5 Lipcolor", "lipcolor");
        ProductRef productRef2 = new ProductRef("pqr", "Chambor eyeliner", "eyeliner");
        List<ProductRef> productRefList = Arrays.asList(productRef1, productRef2);
        VideoDoc videoDocActive = new VideoDoc("guide", "This video will be prepoulated for testing", "This video is inserted as part of testing with MongoDB", "vQ765gh",
                productRefList,
                new UserRef("123", "shabana"));
        videoDocActive.setActive(true);
        videoDocActive = this.mongoTemplate.insert(videoDocActive);
        activeVideo = new VideoLikeUnlikeDto();
        activeVideo.setVideoId(videoDocActive.getId());
        activeVideo.setVideoTitle(videoDocActive.getTitle());
        activeVideo.setUserId(videoDocActive.getUserRef().getId());

        VideoDoc videoDocInactive = new VideoDoc("guide", "This video will be prepoulated for testing", "This video is inserted as part of testing with MongoDB", "vQ765gh",
                productRefList,
                new UserRef("123", "shabana"));
        videoDocInactive.setActive(false);

        inactiveVideo = new VideoLikeUnlikeDto();
        inactiveVideo.setVideoId(videoDocInactive.getId());
        inactiveVideo.setVideoTitle(videoDocInactive.getTitle());
        inactiveVideo.setUserId(videoDocInactive.getUserRef().getId());

    }
}
