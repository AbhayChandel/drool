package com.hexlindia.drool.common.dto.mapper;

import com.hexlindia.drool.common.data.doc.PostRef;
import com.hexlindia.drool.common.dto.PostRefDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PostRefMapperTest {

    @Autowired
    PostRefMapper postRefMapper;

    @Test
    void toDoc() {
        PostRef postRef = postRefMapper.toDoc(new PostRefDto("p123", "THis is a test guide video title", "guide", "video", null));
        assertEquals("p123", postRef.getId());
        assertEquals("THis is a test guide video title", postRef.getTitle());
        assertEquals("guide", postRef.getType());
        assertEquals("video", postRef.getMedium());
    }

    @Test
    void toDto() {
        PostRefDto postRefDto = postRefMapper.toDto(new PostRef("p123", "THis is a test guide video title", "guide", "video", LocalDateTime.parse("16-08-2016 14:22", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))));
        assertEquals("p123", postRefDto.getId());
        assertEquals("THis is a test guide video title", postRefDto.getTitle());
        assertEquals("guide", postRefDto.getType());
        assertEquals("video", postRefDto.getMedium());
        assertNotNull(postRefDto.getDatePosted());
    }
}