package com.hexlindia.drool.post.dto.mapper;

import com.hexlindia.drool.post.dto.PostDto;
import com.hexlindia.drool.video.data.entity.VideoEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PostDtoVideoEntityMapperTest {

    @Autowired
    PostDtoVideoEntityMapper postDtoVideoEntityMapper;

    @Test
    void toEntity() {
        PostDto postDto = new PostDto();
        postDto.setId("1000021");
        postDto.setTitle("This is dummy post");
        postDto.setLikes("1234");
        postDto.setViews("453345");
        postDto.setText("This is dummy text for dummy post");
        postDto.setSourceVideoId("xTsdY78");

        VideoEntity videoEntity = postDtoVideoEntityMapper.toEntity(postDto);

        assertEquals(Long.valueOf("1000021"), videoEntity.getId());
        assertEquals("This is dummy post", videoEntity.getTitle());
        assertEquals(1234, videoEntity.getLikes());
        assertEquals(453345, videoEntity.getViews());
        assertEquals("This is dummy text for dummy post", videoEntity.getText());
        assertEquals("xTsdY78", videoEntity.getSourceVideoId());
    }

    @Test
    void toDto() {
        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setId(154345L);
        videoEntity.setTitle("This is a dummy video entity");
        videoEntity.setLikes(12345);
        videoEntity.setViews(5676545);
        videoEntity.setText("This is dummy text for dummy video entity");
        videoEntity.setSourceVideoId("dgnd67");

        PostDto postDto = postDtoVideoEntityMapper.toDto(videoEntity);

        assertEquals("154345", postDto.getId());
        assertEquals("This is a dummy video entity", postDto.getTitle());
        assertEquals("12345", postDto.getLikes());
        assertEquals("5676545", postDto.getViews());
        assertEquals("This is dummy text for dummy video entity", postDto.getText());
        assertEquals("dgnd67", postDto.getSourceVideoId());
    }
}