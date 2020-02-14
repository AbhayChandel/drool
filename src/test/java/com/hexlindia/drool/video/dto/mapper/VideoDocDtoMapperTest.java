package com.hexlindia.drool.video.dto.mapper;

import com.hexlindia.drool.video.data.doc.ProductRef;
import com.hexlindia.drool.video.data.doc.UserRef;
import com.hexlindia.drool.video.data.doc.VideoDoc;
import com.hexlindia.drool.video.dto.ProductRefDto;
import com.hexlindia.drool.video.dto.UserRefDto;
import com.hexlindia.drool.video.dto.VideoDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class VideoDocDtoMapperTest {

    @Autowired
    VideoDocDtoMapper videoDocDtoMapper;

    @Test
    void toDoc() {
        ProductRefDto productRefDto = new ProductRefDto("p123", "Carolina Herrera 212", "fragrance");
        UserRefDto userRefDto = new UserRefDto("u1123", "User123");
        VideoDto videoDto = new VideoDto("review", "Review of Carolina Herrera 212", "This is an honest review of Carolina Herrera 212", "ch212", productRefDto, userRefDto);
        VideoDoc videoDoc = videoDocDtoMapper.toDoc(videoDto);

        assertEquals("review", videoDoc.getType());
        assertEquals("Review of Carolina Herrera 212", videoDoc.getTitle());
        assertEquals("This is an honest review of Carolina Herrera 212", videoDoc.getDescription());
        assertEquals("ch212", videoDoc.getSourceId());
        assertEquals("p123", videoDoc.getProductRef().getId());
        assertEquals("Carolina Herrera 212", videoDoc.getProductRef().getName());
        assertEquals("fragrance", videoDoc.getProductRef().getType());
        assertEquals("u1123", videoDoc.getUserRef().getId());
        assertEquals("User123", videoDoc.getUserRef().getUsername());
    }

    @Test
    void toDto() {

        ProductRef productRef = new ProductRef("p123", "Carolina Herrera 212", "fragrance");
        UserRef userRef = new UserRef("u1123", "User123");
        VideoDoc videoDoc = new VideoDoc("review", "Review of Carolina Herrera 212", "This is an honest review of Carolina Herrera 212", "ch212", productRef, userRef);
        videoDoc.setId("v123");
        VideoDto videoDto = videoDocDtoMapper.toDto(videoDoc);

        assertEquals("review", videoDto.getType());
        assertEquals("Review of Carolina Herrera 212", videoDto.getTitle());
        assertEquals("This is an honest review of Carolina Herrera 212", videoDto.getDescription());
        assertEquals("ch212", videoDto.getSourceId());
        assertEquals("p123", videoDto.getProductRefDto().getId());
        assertEquals("Carolina Herrera 212", videoDto.getProductRefDto().getName());
        assertEquals("fragrance", videoDto.getProductRefDto().getType());
        assertEquals("u1123", videoDto.getUserRefDto().getId());
        assertEquals("User123", videoDto.getUserRefDto().getUsername());
    }
}