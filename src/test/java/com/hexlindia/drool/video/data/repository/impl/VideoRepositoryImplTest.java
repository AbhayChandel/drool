package com.hexlindia.drool.video.data.repository.impl;

import com.hexlindia.drool.common.data.doc.PostRef;
import com.hexlindia.drool.common.dto.PostRefDto;
import com.hexlindia.drool.common.dto.UserRefDto;
import com.hexlindia.drool.product.data.doc.ProductRef;
import com.hexlindia.drool.user.data.doc.UserRef;
import com.hexlindia.drool.video.data.doc.VideoComment;
import com.hexlindia.drool.video.data.doc.VideoDoc;
import com.hexlindia.drool.video.data.repository.api.VideoRepository;
import com.hexlindia.drool.video.dto.VideoCommentDto;
import com.hexlindia.drool.video.dto.VideoLikeUnlikeDto;
import com.hexlindia.drool.video.dto.VideoThumbnailDataAggregation;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@SpringBootTest
public class VideoRepositoryImplTest {

    private final VideoRepository videoRepository;
    private final MongoTemplate mongoTemplate;

    private VideoLikeUnlikeDto activeVideoLikeUnlikeDto;
    private VideoLikeUnlikeDto inactiveVideoLikeUnlikeDto;
    private String insertedVideoCommentId;

    ObjectId userId = new ObjectId();

    @Autowired
    public VideoRepositoryImplTest(VideoRepository videoRepository, MongoTemplate mongoTemplate) {
        this.videoRepository = videoRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @BeforeEach
    public void setUp() {

        ProductRef productRef1 = new ProductRef("abc", "Lakme 9to5 Lipcolor", "lipcolor");
        ProductRef productRef2 = new ProductRef("pqr", "Chambor eyeliner", "eyeliner");
        List<ProductRef> productRefList = Arrays.asList(productRef1, productRef2);
        VideoDoc videoDocActive = new VideoDoc("guide", "This video will be prepoulated for testing", "This video is inserted as part of testing with MongoDB", "vQ765gh",
                productRefList,
                new UserRef(userId, "shabana"));
        videoDocActive.setActive(true);
        videoDocActive.setDatePosted(LocalDateTime.parse("16-08-2016 14:22", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        videoDocActive = this.mongoTemplate.insert(videoDocActive);
        VideoDoc videoDocActive2 = new VideoDoc("guide", "This video will be prepoulated for testing", "This video is inserted as part of testing with MongoDB", "vQ765gh",
                productRefList,
                new UserRef(userId, "shabana"));
        videoDocActive2.setActive(true);
        videoDocActive2.setDatePosted(LocalDateTime.parse("10-08-2017 14:22", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        videoDocActive = this.mongoTemplate.insert(videoDocActive2);
        VideoDoc videoDocActive3 = new VideoDoc("guide", "This video will be prepoulated for testing", "This video is inserted as part of testing with MongoDB", "vQ765gh",
                productRefList,
                new UserRef(userId, "shabana"));
        videoDocActive3.setActive(true);
        videoDocActive3.setDatePosted(LocalDateTime.parse("10-08-2018 14:22", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        videoDocActive = this.mongoTemplate.insert(videoDocActive3);
        VideoDoc videoDocActive4 = new VideoDoc("guide", "This video will be prepoulated for testing", "This video is inserted as part of testing with MongoDB", "vQ765gh",
                productRefList,
                new UserRef(userId, "shabana"));
        videoDocActive4.setActive(true);
        videoDocActive4.setDatePosted(LocalDateTime.parse("10-08-2019 14:22", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        videoDocActive = this.mongoTemplate.insert(videoDocActive4);
        activeVideoLikeUnlikeDto = new VideoLikeUnlikeDto();
        activeVideoLikeUnlikeDto.setVideoId(videoDocActive.getId().toHexString());
        activeVideoLikeUnlikeDto.setVideoTitle(videoDocActive.getTitle());
        activeVideoLikeUnlikeDto.setUserId(videoDocActive.getUserRef().getId().toHexString());

        VideoDoc videoDocInactive = new VideoDoc("guide", "This video will be prepoulated for testing", "This video is inserted as part of testing with MongoDB", "vQ765gh",
                productRefList,
                new UserRef(userId, "shabana"));
        videoDocInactive.setActive(false);
        videoDocInactive = this.mongoTemplate.insert(videoDocInactive);

        inactiveVideoLikeUnlikeDto = new VideoLikeUnlikeDto();
        inactiveVideoLikeUnlikeDto.setVideoId(videoDocInactive.getId().toHexString());
        inactiveVideoLikeUnlikeDto.setVideoTitle(videoDocInactive.getTitle());
        inactiveVideoLikeUnlikeDto.setUserId(videoDocInactive.getUserRef().getId().toHexString());

        VideoComment videoComment = new VideoComment(new UserRef(userId, "priyanka11"), null, "This is a dummy comment to test insertion");
        PostRef postRef = new PostRef(videoDocActive.getId(), "Title for dummy test post", "guide", "video", null);
        videoComment = videoRepository.insertComment(postRef, videoComment);
        insertedVideoCommentId = videoComment.getId();

        PostRefDto postRefDto = new PostRefDto(videoDocActive.getId().toHexString(), "Title for dummy test post", "guide", "video", null);
        VideoCommentDto videoCommentDto = new VideoCommentDto(postRefDto, new UserRefDto("123", "username1"), "Test comment");
        videoCommentDto.setId(insertedVideoCommentId);
        videoCommentDto.setLikes("0");
        videoRepository.saveCommentLike(videoCommentDto);
    }

    @Test
    public void test_SaveInsert() {
        ObjectId userId = new ObjectId();
        ProductRef productRef1 = new ProductRef("abc", "Lakme 9to5 Lipcolor", "lipcolor");
        ProductRef productRef2 = new ProductRef("pqr", "Chambor eyeliner", "eyeliner");
        List<ProductRef> productRefList = Arrays.asList(productRef1, productRef2);
        VideoDoc videoDoc = new VideoDoc("guide", "This is a test video entry", "This video is inserted as part of testing with MongoDB", "vQ765gh",
                productRefList,
                new UserRef(userId, "shabana"));
        videoDoc = videoRepository.save(videoDoc);
        assertNotNull(videoDoc.getId());
    }

    @Test
    public void test_SaveSave() {
        ObjectId userId = new ObjectId();
        ProductRef productRef1 = new ProductRef("abc", "Lakme 9to5 Lipcolor", "lipcolor");
        ProductRef productRef2 = new ProductRef("pqr", "Chambor eyeliner", "eyeliner");
        List<ProductRef> productRefList = Arrays.asList(productRef1, productRef2);
        VideoDoc videoDoc = new VideoDoc("guide", "This is a test video entry", "This video is inserted as part of testing with MongoDB", "vQ765gh",
                productRefList,
                new UserRef(userId, "shabana"));
        videoDoc = videoRepository.save(videoDoc);
        videoDoc.setSourceId("vQ76");
        videoDoc = videoRepository.save(videoDoc);
        assertEquals("vQ76", videoDoc.getSourceId());
    }

    @Test
    public void test_findByIdActiveTrue() {
        assertNotNull(videoRepository.findByIdAndActiveTrue(new ObjectId(activeVideoLikeUnlikeDto.getVideoId())));
    }

    @Test
    public void test_findByIdActiveFalse() {
        assertFalse(videoRepository.findByIdAndActiveTrue(ObjectId.get()).isPresent());
    }

    @Test
    public void test_getLatestThreeByUser() {
        VideoThumbnailDataAggregation videoThumbnailDataAggregation = videoRepository.getLatestThreeVideosByUser(userId);
        assertEquals(3, videoThumbnailDataAggregation.getVideoThumbnailList().size());
    }

    @Test
    void test_updateReviewId() {
        assertTrue(videoRepository.updateReviewId(new ObjectId(activeVideoLikeUnlikeDto.getVideoId()), new ObjectId()));
    }

    @Test
    public void test_IncrementLikes() {
        videoRepository.saveVideoLikes(activeVideoLikeUnlikeDto);
        assertEquals(1, videoRepository.findByIdAndActiveTrue(new ObjectId(activeVideoLikeUnlikeDto.getVideoId())).get().getLikes());
    }

    @Test
    public void test_DecrementLikes() {
        videoRepository.deleteVideoLikes(activeVideoLikeUnlikeDto);
        assertEquals(-1, videoRepository.findByIdAndActiveTrue(new ObjectId(activeVideoLikeUnlikeDto.getVideoId())).get().getLikes());
    }

    @Test
    public void test_insertComment() {
        VideoComment videoComment = new VideoComment(new UserRef(ObjectId.get(), "priyanka11"), null, "This is a dummy comment to test insertion");
        PostRef postRef = new PostRef(new ObjectId(activeVideoLikeUnlikeDto.getVideoId()), "Title for dummy test post", "guide", "video", null);
        assertNotNull(videoRepository.insertComment(postRef, videoComment));
    }

    @Test
    public void test_UpdateComment() {
        VideoCommentDto videoCommentDto = new VideoCommentDto(new PostRefDto(activeVideoLikeUnlikeDto.getVideoId(), activeVideoLikeUnlikeDto.getVideoTitle(), null, null, null), new UserRefDto("123", "username1"), "This is an update for the comment");
        videoCommentDto.setId(insertedVideoCommentId);
        videoRepository.updateComment(videoCommentDto);
        VideoDoc videoDOc = mongoTemplate.findOne(query(where("_id").is(activeVideoLikeUnlikeDto.getVideoId()).andOperator(where("active").is(true))), VideoDoc.class);
        List<VideoComment> commentList = videoDOc.getCommentList();
        for (VideoComment videoComment : commentList) {
            if (videoComment.getId().equalsIgnoreCase(insertedVideoCommentId)) {
                assertEquals("This is an update for the comment", videoComment.getComment());
                return;
            }
        }
        fail("Video comment was not updated");
    }

    @Test
    public void test_removeComment() {
        ObjectId userId = new ObjectId();
        VideoComment videoComment = new VideoComment(new UserRef(userId, "priyanka11"), null, "This is a dummy comment to test insertion");
        PostRef postRef = new PostRef(new ObjectId(activeVideoLikeUnlikeDto.getVideoId()), "Title for dummy test post", "review", "video", null);
        videoRepository.insertComment(postRef, videoComment);

        VideoCommentDto videoCommentDto = new VideoCommentDto();
        videoCommentDto.setId(videoComment.getId());
        PostRefDto postRefDto = new PostRefDto();
        postRefDto.setId(activeVideoLikeUnlikeDto.getVideoId());
        videoCommentDto.setPostRefDto(postRefDto);
        UserRefDto userRefDto = new UserRefDto();
        userRefDto.setId("123");
        videoCommentDto.setUserRefDto(userRefDto);
        assertTrue(videoRepository.deleteComment(videoCommentDto));
    }

    @Test
    public void test_saveCommentLike() {
        VideoCommentDto videoCommentDto = new VideoCommentDto(new PostRefDto(activeVideoLikeUnlikeDto.getVideoId(), activeVideoLikeUnlikeDto.getVideoTitle(), null, null, null), new UserRefDto("123", "username1"), null);
        videoCommentDto.setId(insertedVideoCommentId);
        videoCommentDto.setLikes("0");
        assertEquals("1", videoRepository.saveCommentLike(videoCommentDto));
    }

    @Test
    public void test_deleteCommentLike() {
        VideoCommentDto videoCommentDto = new VideoCommentDto(new PostRefDto(activeVideoLikeUnlikeDto.getVideoId(), null, null, null, null), new UserRefDto("123", "username1"), null);
        videoCommentDto.setId(insertedVideoCommentId);
        videoCommentDto.setLikes("1");
        assertEquals("0", videoRepository.deleteCommentLike(videoCommentDto));
    }


}
