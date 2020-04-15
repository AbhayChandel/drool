package com.hexlindia.drool.video.dto.mapper;

import com.hexlindia.drool.user.data.doc.UserRef;
import com.hexlindia.drool.video.dto.VideoThumbnail;
import com.hexlindia.drool.video.dto.VideoThumbnailDataAggregation;
import com.hexlindia.drool.video.dto.VideoThumbnailDataDto;
import com.hexlindia.drool.video.dto.VideoThumbnailDto;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class VideoThumbnailDataMapperTest {

    @Autowired
    VideoThumbnailDataMapper videoThumbnailDataMapper;

    @Test
    void toDto() {
        ObjectId userId = new ObjectId();
        VideoThumbnailDataAggregation videoThumbnailDataAggregation = new VideoThumbnailDataAggregation(50, Arrays.asList(new VideoThumbnail("abc123", "s123", "THis is a mapper test video title", 5000, 345, new UserRef(userId, "username1"))));
        VideoThumbnailDataDto videoThumbnailDataDto = videoThumbnailDataMapper.toDto(videoThumbnailDataAggregation);
        assertEquals(50, videoThumbnailDataDto.getTotalVideoCount());
        VideoThumbnailDto videoThumbnailDto = videoThumbnailDataDto.getVideoThumbnailList().get(0);
        assertEquals("abc123", videoThumbnailDto.getId());
        assertEquals("s123", videoThumbnailDto.getSourceId());
        assertEquals("THis is a mapper test video title", videoThumbnailDto.getTitle());
        assertEquals("5k", videoThumbnailDto.getViews());
        assertEquals("345", videoThumbnailDto.getLikes());
        assertEquals(userId, videoThumbnailDto.getUserRef().getId());
    }
}