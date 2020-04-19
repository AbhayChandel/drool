package com.hexlindia.drool.video.dto.mapper;

import com.hexlindia.drool.common.dto.PostRefDto;
import com.hexlindia.drool.common.dto.UserRefDto;
import com.hexlindia.drool.user.data.doc.UserRef;
import com.hexlindia.drool.video.data.doc.VideoComment;
import com.hexlindia.drool.video.dto.VideoCommentDto;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class VideoCommentMapperTest {

    @Autowired
    VideoCommentMapper videoCommentMapper;

    @Test
    void toDto() {
        LocalDateTime datePosted = LocalDateTime.parse("2016-03-04 11:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        ObjectId userId = new ObjectId();
        VideoComment videoComment = new VideoComment(new UserRef(userId, "priyanka11"), datePosted, "This is a comment to test videoCommentMapper toDto()");
        videoComment.setLikes(1119);
        VideoCommentDto videoCommentDto = videoCommentMapper.toDto(videoComment);
        assertEquals(userId.toHexString(), videoCommentDto.getUserRefDto().getId());
        assertEquals("priyanka11", videoCommentDto.getUserRefDto().getUsername());
        assertEquals("4 Mar, 2016 11:30 AM", videoCommentDto.getDatePosted());
        assertEquals("This is a comment to test videoCommentMapper toDto()", videoCommentDto.getComment());
        assertEquals("1.1k", videoCommentDto.getLikes());
    }


    @Test
    void toDoc() {
        ObjectId userId = new ObjectId();
        VideoCommentDto videoCommentDto = new VideoCommentDto(new PostRefDto("p123", "Title for dummy test post", "guide", "video", null), new UserRefDto(userId.toHexString(), "sonam99"), "This is a comment to test videoCommentMapper toDoc");
        ObjectId videoCommentId = ObjectId.get();
        videoCommentDto.setId(videoCommentId.toHexString());
        VideoComment videoComment = videoCommentMapper.toDoc(videoCommentDto);
        assertEquals(userId, videoComment.getUserRef().getId());
        assertEquals("sonam99", videoComment.getUserRef().getUsername());
        assertEquals("This is a comment to test videoCommentMapper toDoc", videoComment.getComment());
        assertEquals(videoCommentId, videoComment.getId());
    }
}