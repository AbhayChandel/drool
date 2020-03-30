package com.hexlindia.drool.video.data.repository.impl;

import com.hexlindia.drool.common.data.doc.PostRef;
import com.hexlindia.drool.common.data.doc.ProductRef;
import com.hexlindia.drool.common.data.doc.UserRef;
import com.hexlindia.drool.common.dto.PostRefDto;
import com.hexlindia.drool.common.dto.UserRefDto;
import com.hexlindia.drool.video.data.doc.VideoComment;
import com.hexlindia.drool.video.data.doc.VideoDoc;
import com.hexlindia.drool.video.data.repository.api.VideoTemplateRepository;
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

@SpringBootTest
public class VideoTemplateRepositoryTest {

    private final VideoTemplateRepository videoTemplateRepository;
    private final MongoTemplate mongoTemplate;

    private VideoLikeUnlikeDto activeVideoLikeUnlikeDto;
    private VideoLikeUnlikeDto inactiveVideoLikeUnlikeDto;
    private String insertedVideoCommentId;

    @Autowired
    public VideoTemplateRepositoryTest(VideoTemplateRepository videoTemplateRepository, MongoTemplate mongoTemplate) {
        this.videoTemplateRepository = videoTemplateRepository;
        this.mongoTemplate = mongoTemplate;
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
        videoDocActive.setDatePosted(LocalDateTime.parse("16-08-2016 14:22", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        videoDocActive = this.mongoTemplate.insert(videoDocActive);
        VideoDoc videoDocActive2 = new VideoDoc("guide", "This video will be prepoulated for testing", "This video is inserted as part of testing with MongoDB", "vQ765gh",
                productRefList,
                new UserRef("123", "shabana"));
        videoDocActive2.setActive(true);
        videoDocActive2.setDatePosted(LocalDateTime.parse("10-08-2017 14:22", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        videoDocActive = this.mongoTemplate.insert(videoDocActive2);
        VideoDoc videoDocActive3 = new VideoDoc("guide", "This video will be prepoulated for testing", "This video is inserted as part of testing with MongoDB", "vQ765gh",
                productRefList,
                new UserRef("123", "shabana"));
        videoDocActive3.setActive(true);
        videoDocActive3.setDatePosted(LocalDateTime.parse("10-08-2018 14:22", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        videoDocActive = this.mongoTemplate.insert(videoDocActive3);
        VideoDoc videoDocActive4 = new VideoDoc("guide", "This video will be prepoulated for testing", "This video is inserted as part of testing with MongoDB", "vQ765gh",
                productRefList,
                new UserRef("123", "shabana"));
        videoDocActive4.setActive(true);
        videoDocActive4.setDatePosted(LocalDateTime.parse("10-08-2019 14:22", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        videoDocActive = this.mongoTemplate.insert(videoDocActive4);
        activeVideoLikeUnlikeDto = new VideoLikeUnlikeDto();
        activeVideoLikeUnlikeDto.setVideoId(videoDocActive.getId());
        activeVideoLikeUnlikeDto.setVideoTitle(videoDocActive.getTitle());
        activeVideoLikeUnlikeDto.setUserId(videoDocActive.getUserRef().getId());

        VideoDoc videoDocInactive = new VideoDoc("guide", "This video will be prepoulated for testing", "This video is inserted as part of testing with MongoDB", "vQ765gh",
                productRefList,
                new UserRef("123", "shabana"));
        videoDocInactive.setActive(false);

        inactiveVideoLikeUnlikeDto = new VideoLikeUnlikeDto();
        inactiveVideoLikeUnlikeDto.setVideoId(videoDocInactive.getId());
        inactiveVideoLikeUnlikeDto.setVideoTitle(videoDocInactive.getTitle());
        inactiveVideoLikeUnlikeDto.setUserId(videoDocInactive.getUserRef().getId());

        VideoComment videoComment = new VideoComment(new UserRef("123", "priyanka11"), null, "This is a dummy comment to test insertion");
        PostRef postRef = new PostRef(videoDocActive.getId(), "Title for dummy test post", "guide", "video", null);
        videoComment = videoTemplateRepository.insertComment(postRef, videoComment);
        insertedVideoCommentId = videoComment.getId();

        PostRefDto postRefDto = new PostRefDto(videoDocActive.getId(), "Title for dummy test post", "guide", "video", null);
        VideoCommentDto videoCommentDto = new VideoCommentDto(postRefDto, new UserRefDto("123", "username1"), "Test comment");
        videoCommentDto.setId(insertedVideoCommentId);
        videoCommentDto.setLikes("0");
        videoTemplateRepository.saveCommentLike(videoCommentDto);
    }

    @Test
    public void test_SaveInsert() {
        ProductRef productRef1 = new ProductRef("abc", "Lakme 9to5 Lipcolor", "lipcolor");
        ProductRef productRef2 = new ProductRef("pqr", "Chambor eyeliner", "eyeliner");
        List<ProductRef> productRefList = Arrays.asList(productRef1, productRef2);
        VideoDoc videoDoc = new VideoDoc("guide", "This is a test video entry", "This video is inserted as part of testing with MongoDB", "vQ765gh",
                productRefList,
                new UserRef("123", "shabana"));
        videoDoc = videoTemplateRepository.save(videoDoc);
        assertNotNull(videoDoc.getId());
    }

    @Test
    public void test_SaveSave() {
        ProductRef productRef1 = new ProductRef("abc", "Lakme 9to5 Lipcolor", "lipcolor");
        ProductRef productRef2 = new ProductRef("pqr", "Chambor eyeliner", "eyeliner");
        List<ProductRef> productRefList = Arrays.asList(productRef1, productRef2);
        VideoDoc videoDoc = new VideoDoc("guide", "This is a test video entry", "This video is inserted as part of testing with MongoDB", "vQ765gh",
                productRefList,
                new UserRef("123", "shabana"));
        videoDoc = videoTemplateRepository.save(videoDoc);
        videoDoc.setSourceId("vQ76");
        videoDoc = videoTemplateRepository.save(videoDoc);
        assertEquals("vQ76", videoDoc.getSourceId());
    }

    @Test
    public void test_findByIdActiveTrue() {
        assertNotNull(videoTemplateRepository.findByIdAndActiveTrue(activeVideoLikeUnlikeDto.getVideoId()));
    }

    @Test
    public void test_findByIdActiveFalse() {
        assertNull(videoTemplateRepository.findByIdAndActiveTrue(inactiveVideoLikeUnlikeDto.getVideoId()));
    }

    @Test
    public void test_getLatestThreeByUser() {
        VideoThumbnailDataAggregation videoThumbnailDataAggregation = videoTemplateRepository.getLatestThreeVideosByUser("123");
        assertEquals(3, videoThumbnailDataAggregation.getVideoThumbnailList().size());
    }

    @Test
    void test_updateReviewId() {
        assertTrue(videoTemplateRepository.updateReviewId(new ObjectId(activeVideoLikeUnlikeDto.getVideoId()), new ObjectId()));
    }

    @Test
    public void test_IncrementLikes() {
        videoTemplateRepository.saveVideoLikes(activeVideoLikeUnlikeDto);
        assertEquals(1, videoTemplateRepository.findByIdAndActiveTrue(activeVideoLikeUnlikeDto.getVideoId()).getLikes());
    }

    @Test
    public void test_DecrementLikes() {
        videoTemplateRepository.deleteVideoLikes(activeVideoLikeUnlikeDto);
        assertEquals(-1, videoTemplateRepository.findByIdAndActiveTrue(activeVideoLikeUnlikeDto.getVideoId()).getLikes());
    }

    @Test
    public void test_insertComment() {
        VideoComment videoComment = new VideoComment(new UserRef("123", "priyanka11"), null, "This is a dummy comment to test insertion");
        PostRef postRef = new PostRef(activeVideoLikeUnlikeDto.getVideoId(), "Title for dummy test post", "guide", "video", null);
        assertNotNull(videoTemplateRepository.insertComment(postRef, videoComment));
    }

    @Test
    public void test_removeComment() {
        VideoComment videoComment = new VideoComment(new UserRef("123", "priyanka11"), null, "This is a dummy comment to test insertion");
        PostRef postRef = new PostRef(activeVideoLikeUnlikeDto.getVideoId(), "Title for dummy test post", "review", "video", null);
        videoTemplateRepository.insertComment(postRef, videoComment);

        VideoCommentDto videoCommentDto = new VideoCommentDto();
        videoCommentDto.setId(videoComment.getId());
        PostRefDto postRefDto = new PostRefDto();
        postRefDto.setId(activeVideoLikeUnlikeDto.getVideoId());
        videoCommentDto.setPostRefDto(postRefDto);
        UserRefDto userRefDto = new UserRefDto();
        userRefDto.setId("123");
        videoCommentDto.setUserRefDto(userRefDto);
        assertTrue(videoTemplateRepository.deleteComment(videoCommentDto));
    }

    @Test
    public void test_saveCommentLike() {
        VideoCommentDto videoCommentDto = new VideoCommentDto(new PostRefDto(activeVideoLikeUnlikeDto.getVideoId(), activeVideoLikeUnlikeDto.getVideoTitle(), null, null, null), new UserRefDto("123", "username1"), null);
        videoCommentDto.setId(insertedVideoCommentId);
        videoCommentDto.setLikes("0");
        assertEquals("1", videoTemplateRepository.saveCommentLike(videoCommentDto));
    }

    @Test
    public void test_deleteCommentLike() {
        VideoCommentDto videoCommentDto = new VideoCommentDto(new PostRefDto(activeVideoLikeUnlikeDto.getVideoId(), null, null, null, null), new UserRefDto("123", "username1"), null);
        videoCommentDto.setId(insertedVideoCommentId);
        videoCommentDto.setLikes("1");
        assertEquals("0", videoTemplateRepository.deleteCommentLike(videoCommentDto));
    }


}
