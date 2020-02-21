package com.hexlindia.drool.user.data.repository.impl;

import com.hexlindia.drool.common.data.doc.CommentRef;
import com.hexlindia.drool.common.data.doc.PostRef;
import com.hexlindia.drool.user.data.doc.UserActivityDoc;
import com.hexlindia.drool.user.data.doc.VideoLike;
import com.hexlindia.drool.user.data.repository.api.UserActivityRepository;
import com.hexlindia.drool.video.dto.VideoLikeUnlikeDto;
import com.mongodb.client.result.UpdateResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@SpringBootTest
class UserActivityRepositoryImplTest {

    private final UserActivityRepository userActivityRepository;
    private final MongoOperations mongoOperations;

    @Autowired
    public UserActivityRepositoryImplTest(UserActivityRepository userActivityRepository, MongoTemplate mongoOperations) {
        this.userActivityRepository = userActivityRepository;
        this.mongoOperations = mongoOperations;
    }

    @Test
    void addVideoLike() {
        VideoLikeUnlikeDto videoLikeUnlikeDto = new VideoLikeUnlikeDto();
        videoLikeUnlikeDto.setVideoId("pqr");
        videoLikeUnlikeDto.setVideoTitle("Testing adding video like");
        videoLikeUnlikeDto.setUserId("987");
        UpdateResult updateResult = this.userActivityRepository.addVideoLike(videoLikeUnlikeDto);
        assertEquals(0, updateResult.getMatchedCount());
        assertNotNull(updateResult.getUpsertedId());
    }

    @Test
    void removeVideoLike() {
        VideoLikeUnlikeDto videoLikeUnlikeDto = new VideoLikeUnlikeDto();
        videoLikeUnlikeDto.setVideoId("abc");
        videoLikeUnlikeDto.setVideoTitle("Testing removing video like");
        videoLikeUnlikeDto.setUserId("123");
        UpdateResult updateResult = this.userActivityRepository.removeVideoLike(videoLikeUnlikeDto);
        assertEquals(1, updateResult.getMatchedCount());
        assertEquals(1, updateResult.getModifiedCount());
    }

    @Test
    void addVideoComment() {
        UpdateResult updateResult = this.userActivityRepository.addVideoComment("123", new CommentRef("c123", "This is a dummy comment to test insertion in UserActivityDoc", new PostRef("v123", "This is a dummy video for testing comment insetion in UserActivityDoc", "video"), LocalDateTime.now()));
        assertTrue(updateResult.getModifiedCount() > 0);
    }

    @BeforeEach
    public void setUp() {
        mongoOperations.upsert(query(where("userId").is("123")), new Update().addToSet("likes.videos", new VideoLike("abc", "This video is part of test setup")), UserActivityDoc.class);
    }
}