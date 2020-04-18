package com.hexlindia.drool.user.data.repository.impl;

import com.hexlindia.drool.common.data.doc.CommentRef;
import com.hexlindia.drool.common.data.doc.PostRef;
import com.hexlindia.drool.common.dto.PostRefDto;
import com.hexlindia.drool.common.dto.UserRefDto;
import com.hexlindia.drool.product.data.doc.ReviewDoc;
import com.hexlindia.drool.user.data.doc.UserActivityDoc;
import com.hexlindia.drool.user.data.doc.UserRef;
import com.hexlindia.drool.user.data.doc.VideoLike;
import com.hexlindia.drool.user.data.repository.api.UserActivityRepository;
import com.hexlindia.drool.video.data.doc.VideoDoc;
import com.hexlindia.drool.video.dto.VideoCommentDto;
import com.hexlindia.drool.video.dto.VideoLikeUnlikeDto;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
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
    void addVideo() {
        UserRef userRef = new UserRef(userId, null);
        VideoDoc videoDoc = new VideoDoc("guide", "This is a dummy video gudie", null, null, null, userRef);
        videoDoc.setId(userId);
        videoDoc.setDatePosted(LocalDateTime.now());
        UpdateResult updateResult = userActivityRepository.addVideo(videoDoc);
        assertEquals(1, updateResult.getModifiedCount());
    }

    @Test
    void addVideoLike() {
        VideoLikeUnlikeDto videoLikeUnlikeDto = new VideoLikeUnlikeDto();
        videoLikeUnlikeDto.setVideoId(ObjectId.get().toHexString());
        videoLikeUnlikeDto.setVideoTitle("Testing adding video like");
        videoLikeUnlikeDto.setUserId(userId.toHexString());
        UpdateResult updateResult = this.userActivityRepository.addVideoLike(videoLikeUnlikeDto);
        assertEquals(1, updateResult.getMatchedCount());
        assertEquals(1, updateResult.getModifiedCount());
    }

    @Test
    void addVideoLikeDuplicate() {
        VideoLikeUnlikeDto videoLikeUnlikeDto = new VideoLikeUnlikeDto();
        videoLikeUnlikeDto.setVideoId(videoId.toHexString());
        videoLikeUnlikeDto.setVideoTitle("This video is part of test setup");
        videoLikeUnlikeDto.setUserId(userId.toHexString());
        UpdateResult updateResult = this.userActivityRepository.addVideoLike(videoLikeUnlikeDto);
        assertEquals(1, updateResult.getMatchedCount());
        assertEquals(0, updateResult.getModifiedCount());
    }

    @Test
    void removeVideoLike() {
        VideoLikeUnlikeDto videoLikeUnlikeDto = new VideoLikeUnlikeDto();
        videoLikeUnlikeDto.setVideoId(videoId.toHexString());
        videoLikeUnlikeDto.setVideoTitle("Testing removing video like");
        videoLikeUnlikeDto.setUserId(userId.toHexString());
        UpdateResult updateResult = this.userActivityRepository.deleteVideoLike(videoLikeUnlikeDto);
        assertEquals(1, updateResult.getMatchedCount());
        assertEquals(1, updateResult.getModifiedCount());
    }

    @Test
    void addVideoComment() {
        UpdateResult updateResult = this.userActivityRepository.addVideoComment(userId, new CommentRef(commentId, "This is a dummy comment to test insertion in UserActivityDoc", new PostRef(ObjectId.get(), "This is a dummy video for testing comment insetion in UserActivityDoc", "guide", "video", null), LocalDateTime.now()));
        assertTrue(updateResult.getModifiedCount() > 0);
    }

    @Test
    void removeVideoComment() {
        VideoCommentDto videoCommentDto = new VideoCommentDto(new PostRefDto("", "", "", "", ""), new UserRefDto(userId.toHexString(), ""), null);
        videoCommentDto.setId(commentId.toHexString());
        UpdateResult updateResult = this.userActivityRepository.deleteVideoComment(videoCommentDto);
        assertTrue(updateResult.getModifiedCount() > 0);
    }

    @Test
    void addCommentLike() {
        VideoCommentDto videoCommentDto = new VideoCommentDto(new PostRefDto(ObjectId.get().toHexString(), "This is a test insert", "review", "video", null), new UserRefDto("456", "username1"), "This is a test comment");
        videoCommentDto.setId(commentId.toHexString());
        UpdateResult updateResult = this.userActivityRepository.addCommentLike(videoCommentDto);
        assertEquals(0, updateResult.getMatchedCount());
        assertNotNull(updateResult.getUpsertedId());
    }

    @Test
    void deleteCommentLike() {
        VideoCommentDto videoCommentDto = new VideoCommentDto(new PostRefDto("p123", null, null, null, null), new UserRefDto(userId.toHexString(), null), null);
        videoCommentDto.setId(commentId.toHexString());
        UpdateResult updateResult = this.userActivityRepository.deleteCommentLike(videoCommentDto);
        assertTrue(updateResult.getModifiedCount() > 0);
    }

    @Test
    void addTextReview() {
        ReviewDoc reviewDoc = new ReviewDoc();
        reviewDoc.setUserRef(new UserRef(userId, "shabana"));
        reviewDoc.setId(ObjectId.get());
        reviewDoc.setReviewSummary("This is a great dummy review summary");
        reviewDoc.setDatePosted(LocalDateTime.now());
        assertTrue(userActivityRepository.addTextReview(reviewDoc).getModifiedCount() > 0);
    }

    private ObjectId userId = new ObjectId();
    private ObjectId videoId = new ObjectId();
    private ObjectId commentId = new ObjectId();

    @BeforeEach
    public void setUp() {
        mongoOperations.upsert(query(where("id").is(userId)), new Update().addToSet("likes.videos", new VideoLike(videoId, "This video is part of test setup")), UserActivityDoc.class);
        mongoOperations.upsert(query(where("id").is(userId)), new Update().addToSet("comments", new CommentRef(commentId, "This is a test comment", new PostRef(ObjectId.get(), "This is a test post.", "guide", "video", null), LocalDateTime.now())), UserActivityDoc.class);
        mongoOperations.upsert(query(where("id").is(userId)), new Update().addToSet("likes.comments", new CommentRef(commentId, "A test comment", new PostRef(ObjectId.get(), "a test post", null, null, null), null)), UserActivityDoc.class);
    }

}