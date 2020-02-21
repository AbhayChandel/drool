package com.hexlindia.drool.common.dto.mapper;

import com.hexlindia.drool.common.data.doc.PostRef;
import com.hexlindia.drool.common.dto.PostRefDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PostRefMapperTest {

    @Autowired
    PostRefMapper postRefMapper;

    @Test
    void toDoc() {
        PostRef postRef = postRefMapper.toDoc(new PostRefDto("p123", "THis is a test guide video title", "video"));
        assertEquals("p123", postRef.getId());
        assertEquals("THis is a test guide video title", postRef.getTitle());
        assertEquals("video", postRef.getType());
    }

    @Test
    void toDto() {
        PostRefDto postRefDto = postRefMapper.toDto(new PostRef("p123", "THis is a test guide video title", "video"));
        assertEquals("p123", postRefDto.getId());
        assertEquals("THis is a test guide video title", postRefDto.getTitle());
        assertEquals("video", postRefDto.getType());
    }
}