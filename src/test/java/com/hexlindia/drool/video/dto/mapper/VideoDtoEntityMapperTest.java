package com.hexlindia.drool.video.dto.mapper;

import com.hexlindia.drool.common.data.constant.PostType;
import com.hexlindia.drool.post.data.entity.VideoEntity;
import com.hexlindia.drool.video.dto.VideoDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class VideoDtoEntityMapperTest {

    @Autowired
    VideoDtoEntityMapper videoDtoEntityMapper;

    @Test
    void toEntity() {
        VideoDto videoDto = new VideoDto();
        videoDto.setTitle("This is a dummy video");
        videoDto.setSourceId("xYd123Zrt");
        videoDto.setDescription("This video is created for the purpose of testing");
        videoDto.setUserId("1000012");
        videoDto.setPostType(PostType.guide);

        VideoEntity videoEntity = videoDtoEntityMapper.toEntity(videoDto);

        assertEquals("This is a dummy video", videoEntity.getTitle());
        assertEquals("xYd123Zrt", videoEntity.getSourceVideoId());
        assertEquals("This video is created for the purpose of testing", videoEntity.getText());

    }
}