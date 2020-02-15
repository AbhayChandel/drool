package com.hexlindia.drool.video.dto.mapper;

import com.hexlindia.drool.video.data.doc.ProductRef;
import com.hexlindia.drool.video.data.doc.UserRef;
import com.hexlindia.drool.video.data.doc.VideoComment;
import com.hexlindia.drool.video.data.doc.VideoDoc;
import com.hexlindia.drool.video.dto.ProductRefDto;
import com.hexlindia.drool.video.dto.UserRefDto;
import com.hexlindia.drool.video.dto.VideoDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VideoDocDtoMapperTest {

    @Autowired
    VideoDocDtoMapper videoDocDtoMapper;

    @Test
    void toDoc() {
        UserRefDto userRefDto = new UserRefDto("u1123", "User123");
        VideoDto videoDto = new VideoDto("review", "Review of Carolina Herrera 212", "This is an honest review of Carolina Herrera 212", "ch212", Arrays.asList(new ProductRefDto("p123", "Carolina Herrera 212", "fragrance")), userRefDto);
        videoDto.setActive(true);
        VideoDoc videoDoc = videoDocDtoMapper.toDoc(videoDto);

        assertEquals("review", videoDoc.getType());
        assertTrue(videoDoc.isActive());
        assertEquals("Review of Carolina Herrera 212", videoDoc.getTitle());
        assertEquals("This is an honest review of Carolina Herrera 212", videoDoc.getDescription());
        assertEquals("ch212", videoDoc.getSourceId());
        assertEquals("p123", videoDoc.getProductRefList().get(0).getId());
        assertEquals("Carolina Herrera 212", videoDoc.getProductRefList().get(0).getName());
        assertEquals("fragrance", videoDoc.getProductRefList().get(0).getType());
        assertEquals("u1123", videoDoc.getUserRef().getId());
        assertEquals("User123", videoDoc.getUserRef().getUsername());
    }

    @Test
    void toDto() {

        UserRef userRef = new UserRef("u1123", "User123");
        VideoDoc videoDoc = new VideoDoc("review", "Review of Carolina Herrera 212", "This is an honest review of Carolina Herrera 212", "ch212", Arrays.asList(new ProductRef("p123", "Carolina Herrera 212", "fragrance")), userRef);
        videoDoc.setActive(false);
        videoDoc.setId("v123");
        videoDoc.setViews(94587656);
        videoDoc.setLikes(1546);
        VideoComment videoComment13 = new VideoComment(new UserRef("2", "priya21"), LocalDateTime.now(), "For a celebrity makeup line the prices are so affordable.. otherwise you can see kylie, kim and other big youtubers who launch their makeup line and their prices are sky-high");
        videoComment13.setLikes(13333);
        VideoComment videoComment14 = new VideoComment(new UserRef("3", "sonam31"), LocalDateTime.now(), "Instead of collaborating with Nyka people should collaborate with sugar!! Sugar cosmetics are so much better and underrated for some reason.");
        videoComment14.setLikes(760000);
        List<VideoComment> videoCommentList1 = Arrays.asList(videoComment13, videoComment14);
        videoDoc.setVideoCommentList(videoCommentList1);
        VideoDto videoDto = videoDocDtoMapper.toDto(videoDoc);

        assertEquals("review", videoDto.getType());
        assertFalse(videoDto.isActive());
        assertEquals("Review of Carolina Herrera 212", videoDto.getTitle());
        assertEquals("This is an honest review of Carolina Herrera 212", videoDto.getDescription());
        assertEquals("ch212", videoDto.getSourceId());
        assertEquals("94.5M", videoDto.getViews());
        assertEquals("1.5k", videoDto.getLikes());
        assertEquals("p123", videoDto.getProductRefDtoList().get(0).getId());
        assertEquals("Carolina Herrera 212", videoDto.getProductRefDtoList().get(0).getName());
        assertEquals("fragrance", videoDto.getProductRefDtoList().get(0).getType());
        assertEquals("u1123", videoDto.getUserRefDto().getId());
        assertEquals("User123", videoDto.getUserRefDto().getUsername());
        assertEquals(2, videoDto.getVideoCommentDtoList().size());
        assertEquals("13.3k", videoDto.getVideoCommentDtoList().get(0).getLikes());
        assertEquals("760k", videoDto.getVideoCommentDtoList().get(1).getLikes());
    }
}