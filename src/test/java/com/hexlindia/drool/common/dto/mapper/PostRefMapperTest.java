package com.hexlindia.drool.common.dto.mapper;

import com.hexlindia.drool.common.data.constant.PostMedium;
import com.hexlindia.drool.common.data.constant.PostType;
import com.hexlindia.drool.common.data.doc.PostRef;
import com.hexlindia.drool.common.dto.PostRefDto;
import org.bson.types.ObjectId;
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
        ObjectId postId = new ObjectId();
        PostRef postRef = postRefMapper.toDoc(new PostRefDto(postId.toHexString(), "THis is a test guide video title", PostType.guide, PostMedium.video, null));
        assertEquals(postId, postRef.getId());
        assertEquals("THis is a test guide video title", postRef.getTitle());
        assertEquals(PostType.guide, postRef.getType());
        assertEquals(PostMedium.video, postRef.getMedium());
    }

    @Test
    void toDto() {
        ObjectId postId = new ObjectId();
        PostRefDto postRefDto = postRefMapper.toDto(new PostRef(postId, "THis is a test guide video title", PostType.guide, PostMedium.video, LocalDateTime.parse("16-08-2016 14:22", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))));
        assertEquals(postId.toHexString(), postRefDto.getId());
        assertEquals("THis is a test guide video title", postRefDto.getTitle());
        assertEquals(PostType.guide, postRefDto.getType());
        assertEquals(PostMedium.video, postRefDto.getMedium());
        assertNotNull(postRefDto.getDatePosted());
    }
}