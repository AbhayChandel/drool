package com.hexlindia.drool.discussion.dto.mapper;

import com.hexlindia.drool.common.dto.PostRefDto;
import com.hexlindia.drool.common.dto.UserRefDto;
import com.hexlindia.drool.discussion.data.doc.DiscussionReplyDoc;
import com.hexlindia.drool.discussion.dto.DiscussionReplyDto;
import com.hexlindia.drool.user.data.doc.UserRef;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DiscussionReplyDtoDOcMapperTest {

    @Autowired
    DiscussionReplyDtoDocMapper discussionReplyDtoDocMapper;

    @Test
    void toDoc() {
        DiscussionReplyDto discussionReplyDto = new DiscussionReplyDto();
        discussionReplyDto.setPostRefDto(new PostRefDto());
        discussionReplyDto.setReply("This is going to be a great reply");
        ObjectId userId = ObjectId.get();
        discussionReplyDto.setUserRefDto(new UserRefDto(userId.toHexString(), "shabana"));

        DiscussionReplyDoc discussionReplyDoc = discussionReplyDtoDocMapper.toDoc(discussionReplyDto);
        assertEquals("This is going to be a great reply", discussionReplyDoc.getReply());
        assertEquals(userId, discussionReplyDoc.getUserRef().getId());
        assertEquals("shabana", discussionReplyDoc.getUserRef().getUsername());
    }

    @Test
    void toDto() {
        DiscussionReplyDoc discussionReplyDoc = new DiscussionReplyDoc();
        discussionReplyDoc.setReply("As I told it is a great reply");
        ObjectId userId = ObjectId.get();
        discussionReplyDoc.setUserRef(new UserRef(userId, "shabana"));
        discussionReplyDoc.setLikes(190);

        DiscussionReplyDto discussionReplyDto = discussionReplyDtoDocMapper.toDto(discussionReplyDoc);
        assertEquals("As I told it is a great reply", discussionReplyDto.getReply());
        assertEquals(userId.toHexString(), discussionReplyDto.getUserRefDto().getId());
        assertEquals("shabana", discussionReplyDto.getUserRefDto().getUsername());
        assertEquals("190", discussionReplyDto.getLikes());
    }
}