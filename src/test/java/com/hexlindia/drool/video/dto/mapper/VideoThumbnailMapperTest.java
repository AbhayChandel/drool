package com.hexlindia.drool.video.dto.mapper;

import com.hexlindia.drool.common.data.doc.UserRef;
import com.hexlindia.drool.video.dto.VideoThumbnail;
import com.hexlindia.drool.video.dto.VideoThumbnailDto;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class VideoThumbnailMapperTest {

    @Autowired
    VideoThumbnailMapper videoThumbnailMapper;

    @Test
    void toDto() {
        ObjectId userId = new ObjectId();
        VideoThumbnail videoThumbnail = new VideoThumbnail("abc123", "s123", "THis is a mapper test video title", 5000, 345, new UserRef(userId, "username1"));
        VideoThumbnailDto videoThumbnailDto = videoThumbnailMapper.toDto(videoThumbnail);
        assertEquals("abc123", videoThumbnailDto.getId());
        assertEquals("s123", videoThumbnailDto.getSourceId());
        assertEquals("THis is a mapper test video title", videoThumbnailDto.getTitle());
        assertEquals("5k", videoThumbnailDto.getViews());
        assertEquals("345", videoThumbnailDto.getLikes());
        assertEquals(userId, videoThumbnailDto.getUserRef().getId());
    }
}