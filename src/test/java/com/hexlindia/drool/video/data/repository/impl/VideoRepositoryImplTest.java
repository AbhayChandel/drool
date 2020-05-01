package com.hexlindia.drool.video.data.repository.impl;

import com.hexlindia.drool.common.data.constant.PostMedium;
import com.hexlindia.drool.common.data.constant.PostType;
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
import com.mongodb.client.result.DeleteResult;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@SpringBootTest
public class VideoRepositoryImplTest {

    private final VideoRepository videoRepository;
    private final MongoTemplate mongoTemplate;

    private VideoLikeUnlikeDto activeVideoLikeUnlikeDto;
    private VideoLikeUnlikeDto inactiveVideoLikeUnlikeDto;
    private ObjectId insertedVideoCommentId;

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
        VideoDoc videoDocActive = new VideoDoc(PostType.guide, "This video will be prepoulated for testing", "This video is inserted as part of testing with MongoDB", "vQ765gh",
                productRefList,
                new UserRef(userId, "shabana"));
        videoDocActive.setActive(true);
        videoDocActive.setDatePosted(LocalDateTime.parse("16-08-2016 14:22", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        videoDocActive = this.mongoTemplate.insert(videoDocActive);
        VideoDoc videoDocActive2 = new VideoDoc(PostType.guide, "This video will be prepoulated for testing", "This video is inserted as part of testing with MongoDB", "vQ765gh",
                productRefList,
                new UserRef(userId, "shabana"));
        videoDocActive2.setActive(true);
        videoDocActive2.setDatePosted(LocalDateTime.parse("10-08-2017 14:22", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        videoDocActive = this.mongoTemplate.insert(videoDocActive2);
        VideoDoc videoDocActive3 = new VideoDoc(PostType.guide, "This video will be prepoulated for testing", "This video is inserted as part of testing with MongoDB", "vQ765gh",
                productRefList,
                new UserRef(userId, "shabana"));
        videoDocActive3.setActive(true);
        videoDocActive3.setDatePosted(LocalDateTime.parse("10-08-2018 14:22", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        videoDocActive = this.mongoTemplate.insert(videoDocActive3);
        VideoDoc videoDocActive4 = new VideoDoc(PostType.guide, "This video will be prepoulated for testing", "This video is inserted as part of testing with MongoDB", "vQ765gh",
                productRefList,
                new UserRef(userId, "shabana"));
        videoDocActive4.setActive(true);
        videoDocActive4.setDatePosted(LocalDateTime.parse("10-08-2019 14:22", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        List<VideoComment> commentList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            commentList.add(new VideoComment());
        }
        videoDocActive4.setCommentList(commentList);
        videoDocActive = this.mongoTemplate.insert(videoDocActive4);
        activeVideoLikeUnlikeDto = new VideoLikeUnlikeDto();
        activeVideoLikeUnlikeDto.setVideoId(videoDocActive.getId().toHexString());
        activeVideoLikeUnlikeDto.setVideoTitle(videoDocActive.getTitle());
        activeVideoLikeUnlikeDto.setUserId(videoDocActive.getUserRef().getId().toHexString());

        VideoDoc videoDocInactive = new VideoDoc(PostType.guide, "This video will be prepoulated for testing", "This video is inserted as part of testing with MongoDB", "vQ765gh",
                productRefList,
                new UserRef(userId, "shabana"));
        videoDocInactive.setActive(false);
        videoDocInactive = this.mongoTemplate.insert(videoDocInactive);

        inactiveVideoLikeUnlikeDto = new VideoLikeUnlikeDto();
        inactiveVideoLikeUnlikeDto.setVideoId(videoDocInactive.getId().toHexString());
        inactiveVideoLikeUnlikeDto.setVideoTitle(videoDocInactive.getTitle());
        inactiveVideoLikeUnlikeDto.setUserId(videoDocInactive.getUserRef().getId().toHexString());

        VideoComment videoComment = new VideoComment(new UserRef(userId, "priyanka11"), null, "This is a dummy comment to test insertion");
        PostRef postRef = new PostRef(videoDocActive.getId(), "Title for dummy test post", PostType.guide, PostMedium.video, null);
        videoComment = videoRepository.insertComment(postRef, videoComment);
        insertedVideoCommentId = videoComment.getId();

        PostRefDto postRefDto = new PostRefDto(videoDocActive.getId().toHexString(), "Title for dummy test post", PostType.guide, PostMedium.video, null);
        VideoCommentDto videoCommentDto = new VideoCommentDto(postRefDto, new UserRefDto("123", "username1"), "Test comment");
        videoCommentDto.setId(insertedVideoCommentId.toHexString());
        videoCommentDto.setLikes("0");
        videoRepository.saveCommentLike(videoCommentDto);
    }

    @Test
    public void test_SaveInsert() {
        ObjectId userId = new ObjectId();
        ProductRef productRef1 = new ProductRef("abc", "Lakme 9to5 Lipcolor", "lipcolor");
        ProductRef productRef2 = new ProductRef("pqr", "Chambor eyeliner", "eyeliner");
        List<ProductRef> productRefList = Arrays.asList(productRef1, productRef2);
        VideoDoc videoDoc = new VideoDoc(PostType.guide, "This is a test video entry", "This video is inserted as part of testing with MongoDB", "vQ765gh",
                productRefList,
                new UserRef(userId, "shabana"));
        videoDoc = videoRepository.save(videoDoc);
        assertNotNull(videoDoc.getId());
    }

    @Test
    void updateVideo() {
        ProductRef productRef1 = new ProductRef("abc", "Lakme 9to5 Lipcolor", "lipcolor");
        ProductRef productRef2 = new ProductRef("pqr", "Chambor eyeliner", "eyeliner");
        List<ProductRef> productRefList = Arrays.asList(productRef1, productRef2);
        VideoDoc videoDoc = new VideoDoc(PostType.guide, "Title Updated", "Description Updated", "vQ765gh",
                productRefList,
                new UserRef(userId, "shabana"));
        videoDoc.setId(new ObjectId(activeVideoLikeUnlikeDto.getVideoId()));
        boolean result = videoRepository.updateVideo(videoDoc);
        assertTrue(result);

        VideoDoc videoDocFromDB = mongoTemplate.findOne(Query.query(new Criteria("_id").is(new ObjectId(activeVideoLikeUnlikeDto.getVideoId())).andOperator(new Criteria("active").is(true))), VideoDoc.class);
        assertEquals("Title Updated", videoDocFromDB.getTitle());
        assertEquals("Description Updated", videoDocFromDB.getDescription());
        assertEquals("abc", videoDocFromDB.getProductRefList().get(0).getId());
        assertEquals("Lakme 9to5 Lipcolor", videoDocFromDB.getProductRefList().get(0).getName());
        assertEquals("lipcolor", videoDocFromDB.getProductRefList().get(0).getType());
        assertEquals("pqr", videoDocFromDB.getProductRefList().get(1).getId());
        assertEquals("Chambor eyeliner", videoDocFromDB.getProductRefList().get(1).getName());
        assertEquals("eyeliner", videoDocFromDB.getProductRefList().get(1).getType());
    }

    @Test
    public void test_findByIdActiveTrue() {
        Optional<VideoDoc> videoDocOptional = videoRepository.findByIdAndActiveTrue(new ObjectId(activeVideoLikeUnlikeDto.getVideoId()));
        assertTrue(videoDocOptional.isPresent());
        VideoDoc videoDoc = videoDocOptional.get();
        assertEquals(101, videoDoc.getTotalComments());
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
        PostRef postRef = new PostRef(new ObjectId(activeVideoLikeUnlikeDto.getVideoId()), "Title for dummy test post", PostType.guide, PostMedium.video, null);
        assertNotNull(videoRepository.insertComment(postRef, videoComment));
    }

    @Test
    public void test_UpdateComment() {
        VideoCommentDto videoCommentDto = new VideoCommentDto(new PostRefDto(activeVideoLikeUnlikeDto.getVideoId(), activeVideoLikeUnlikeDto.getVideoTitle(), null, null, null), new UserRefDto("123", "username1"), "This is an update for the comment");
        videoCommentDto.setId(insertedVideoCommentId.toHexString());
        videoRepository.updateComment(videoCommentDto);
        VideoDoc videoDOc = mongoTemplate.findOne(query(where("_id").is(activeVideoLikeUnlikeDto.getVideoId()).andOperator(where("active").is(true))), VideoDoc.class);
        List<VideoComment> commentList = videoDOc.getCommentList();
        for (VideoComment videoComment : commentList) {
            if (videoComment.getId().equals(insertedVideoCommentId)) {
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
        PostRef postRef = new PostRef(new ObjectId(activeVideoLikeUnlikeDto.getVideoId()), "Title for dummy test post", PostType.review, PostMedium.video, null);
        videoRepository.insertComment(postRef, videoComment);

        VideoCommentDto videoCommentDto = new VideoCommentDto();
        videoCommentDto.setId(videoComment.getId().toHexString());
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
        videoCommentDto.setId(insertedVideoCommentId.toHexString());
        videoCommentDto.setLikes("0");
        assertEquals("1", videoRepository.saveCommentLike(videoCommentDto));
    }

    @Test
    public void test_deleteCommentLike() {
        VideoCommentDto videoCommentDto = new VideoCommentDto(new PostRefDto(activeVideoLikeUnlikeDto.getVideoId(), null, null, null, null), new UserRefDto("123", "username1"), null);
        videoCommentDto.setId(insertedVideoCommentId.toHexString());
        videoCommentDto.setLikes("1");
        assertEquals("0", videoRepository.deleteCommentLike(videoCommentDto));
    }

    @Test
    void deleteVideo() {
        DeleteResult result = videoRepository.deleteVideo(new ObjectId(activeVideoLikeUnlikeDto.getVideoId()));
        assertEquals(1, result.getDeletedCount());
    }
}
