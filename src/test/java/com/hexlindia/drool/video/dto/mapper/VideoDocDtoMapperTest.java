package com.hexlindia.drool.video.dto.mapper;

import com.hexlindia.drool.common.data.constant.PostType;
import com.hexlindia.drool.common.dto.UserRefDto;
import com.hexlindia.drool.product.data.doc.ProductRef;
import com.hexlindia.drool.product.dto.ProductRefDto;
import com.hexlindia.drool.user.data.doc.UserRef;
import com.hexlindia.drool.video.data.doc.VideoComment;
import com.hexlindia.drool.video.data.doc.VideoDoc;
import com.hexlindia.drool.video.dto.VideoDtoMOngo;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VideoDocDtoMapperTest {

    @Autowired
    VideoDocDtoMapper videoDocDtoMapper;

    @Test
    void toDoc() {
        ObjectId userId = new ObjectId();
        UserRefDto userRefDto = new UserRefDto(userId.toHexString(), "User123");
        VideoDtoMOngo videoDtoMOngo = new VideoDtoMOngo(PostType.review, "Review of Carolina Herrera 212", "This is an honest review of Carolina Herrera 212", "ch212", Arrays.asList(new ProductRefDto("p123", "Carolina Herrera 212", "fragrance")), userRefDto);
        videoDtoMOngo.setActive(true);
        VideoDoc videoDoc = videoDocDtoMapper.toDoc(videoDtoMOngo);

        assertEquals(PostType.review, videoDoc.getType());
        assertTrue(videoDoc.isActive());
        assertEquals("Review of Carolina Herrera 212", videoDoc.getTitle());
        assertEquals("This is an honest review of Carolina Herrera 212", videoDoc.getDescription());
        assertEquals("ch212", videoDoc.getSourceId());
        assertEquals("p123", videoDoc.getProductRefList().get(0).getId());
        assertEquals("Carolina Herrera 212", videoDoc.getProductRefList().get(0).getName());
        assertEquals("fragrance", videoDoc.getProductRefList().get(0).getType());
        assertEquals(userId, videoDoc.getUserRef().getId());
        assertEquals("User123", videoDoc.getUserRef().getUsername());
    }

    @Test
    void toDto() {
        ObjectId userId = new ObjectId();
        UserRef userRef = new UserRef(userId, "User123");
        VideoDoc videoDoc = new VideoDoc(PostType.review, "Review of Carolina Herrera 212", "This is an honest review of Carolina Herrera 212", "ch212", Arrays.asList(new ProductRef("p123", "Carolina Herrera 212", "fragrance")), userRef);
        videoDoc.setActive(false);
        videoDoc.setId(ObjectId.get());
        videoDoc.setViews(94587656);
        videoDoc.setLikes(1546);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        videoDoc.setDatePosted(LocalDateTime.parse("2020-02-04 19:00", formatter));

        VideoComment videoComment13 = new VideoComment(new UserRef(ObjectId.get(), "priya21"), LocalDateTime.parse("2020-02-05 09:00", formatter), "For a celebrity makeup line the prices are so affordable.. otherwise you can see kylie, kim and other big youtubers who launch their makeup line and their prices are sky-high");
        videoComment13.setLikes(13333);
        VideoComment videoComment14 = new VideoComment(new UserRef(ObjectId.get(), "sonam31"), LocalDateTime.parse("2020-02-15 21:30", formatter), "Instead of collaborating with Nyka people should collaborate with sugar!! Sugar cosmetics are so much better and underrated for some reason.");
        videoComment14.setLikes(760000);
        List<VideoComment> videoCommentList1 = Arrays.asList(videoComment13, videoComment14);
        videoDoc.setCommentList(videoCommentList1);
        VideoDtoMOngo videoDtoMOngo = videoDocDtoMapper.toDto(videoDoc);

        assertEquals(PostType.review, videoDtoMOngo.getType());
        assertFalse(videoDtoMOngo.isActive());
        assertEquals("Review of Carolina Herrera 212", videoDtoMOngo.getTitle());
        assertEquals("This is an honest review of Carolina Herrera 212", videoDtoMOngo.getDescription());
        assertEquals("ch212", videoDtoMOngo.getSourceId());
        assertEquals("4 Feb, 2020", videoDtoMOngo.getDatePosted());
        assertEquals("94.5M", videoDtoMOngo.getViews());
        assertEquals("1.5k", videoDtoMOngo.getLikes());
        assertEquals("p123", videoDtoMOngo.getProductRefDtoList().get(0).getId());
        assertEquals("Carolina Herrera 212", videoDtoMOngo.getProductRefDtoList().get(0).getName());
        assertEquals("fragrance", videoDtoMOngo.getProductRefDtoList().get(0).getType());
        assertEquals(userId.toHexString(), videoDtoMOngo.getUserRefDto().getId());
        assertEquals("User123", videoDtoMOngo.getUserRefDto().getUsername());
        assertEquals(2, videoDtoMOngo.getVideoCommentDtoList().size());
        assertEquals("13.3k", videoDtoMOngo.getVideoCommentDtoList().get(0).getLikes());
        assertEquals("5 Feb, 2020 9:00 AM", videoDtoMOngo.getVideoCommentDtoList().get(0).getDatePosted());
        assertEquals("760k", videoDtoMOngo.getVideoCommentDtoList().get(1).getLikes());
        assertEquals("15 Feb, 2020 9:30 PM", videoDtoMOngo.getVideoCommentDtoList().get(1).getDatePosted());
    }
}